package com.avalanche.tmcs.matching;

import com.avalanche.tmcs.job_posting.JobPosting;
import com.avalanche.tmcs.job_posting.JobPostingDAO;
import com.avalanche.tmcs.students.Student;
import com.avalanche.tmcs.students.StudentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Predicate;

/**
 * Matches students and job postings
 *
 * @author David Dubois
 * @since 16-Apr-17.
 */
@Service
public class MatchingService {
    private MatchDAO matchDAO;

    private StudentDAO studentDAO;

    private JobPostingDAO jobPostingDAO;

    private Executor executor = Executors.newFixedThreadPool(10);

    private final float requiredSkillsWeight = 0.8f;

    @Autowired
    public MatchingService(MatchDAO matchDAO, StudentDAO studentDAO, JobPostingDAO jobPostingDAO) {
        this.matchDAO = matchDAO;
        this.studentDAO = studentDAO;
        this.jobPostingDAO = jobPostingDAO;
    }

    /**
     * Registers the given student with the matching student
     * <p>This adds the student to the skills index and calculates the best matches for this student</p>
     * <p>The expectation is that this method will be called when a new student is registered</p>
     * <p>This method uses a thread to perform the actual matching, so calling it should be very fast</p>
     *
     * @param student The student to register
     */
    public void registerStudent(final Student student) {
        executor.execute(() -> {
            final Map<JobPosting, MatchedSkillsCount> matchedSkillsCountMap = new HashMap<>();
            final List<Match> studentMatches = matchDAO.findAllByStudent(student);
            Predicate<JobPosting> newJobFilter = posting -> studentMatches.stream().noneMatch(match -> match.getJob().equals(posting));
            for (Skill skill : student.getSkills()) {
                // This does one DB call for each skill in the student's profile
                // Based on what I've seen on LinkedIn, I'd expect students to have maybe a hundred skill max? That's
                // 100 calls. Would be better if we didn't need that many, but should be fine for a POC
                List<JobPosting> postingsWithAtLeastOneRequiredSkill = jobPostingDAO.findAllByRequiredSkillsContains(skill);
                postingsWithAtLeastOneRequiredSkill.stream()
                        .filter(newJobFilter)
                        .forEach(posting -> {
                            if(!matchedSkillsCountMap.containsKey(posting)) {
                                matchedSkillsCountMap.put(posting, new MatchedSkillsCount());
                            }
                            matchedSkillsCountMap.get(posting).requiredSkillsCount += 1;
                        });

                List<JobPosting> postingsWithAtLeastOneRecommendedSkill = jobPostingDAO.findAllByNiceToHaveSkillsContains(skill);
                postingsWithAtLeastOneRecommendedSkill.stream()
                        .filter(newJobFilter)
                        .forEach(posting -> {
                            if(!matchedSkillsCountMap.containsKey(posting)) {
                                matchedSkillsCountMap.put(posting, new MatchedSkillsCount());
                            }
                            matchedSkillsCountMap.get(posting).recommendedSkillsCount += 1;
                        });
            }

            final List<Match> matches = buildMatchesList(student, matchedSkillsCountMap);

            matchDAO.save(matches);
        });
    }

    /**
     * Registers the given job posting with the matching service
     * <p>This adds the job posting to the skills index and calculates the best matches for this job posting</p>
     * <p>The expectation is that this method will be called when a job posting is registered</p>
     * <p>This method uses a thread to perform the actual matching, so calling it should be very fast</p>
     *
     * @param posting The job posting to register
     */
    public void registerJobPosting(final JobPosting posting) {
        executor.execute(() -> {
            final Map<Student, Integer> numberOfMatchedRequiredSkills = countStudentsWithSkillInList(posting.getRequiredSkills());
            final Map<Student, Integer> numberOfMatchedRecommendedSkills = countStudentsWithSkillInList(posting.getNiceToHaveSkills());

            final List<Match> matches = new ArrayList<>();
            for(Student student : numberOfMatchedRequiredSkills.keySet()) {
                float weight = numberOfMatchedRequiredSkills.get(student) * requiredSkillsWeight;

                if(numberOfMatchedRecommendedSkills.containsKey(student)) {
                    weight += numberOfMatchedRecommendedSkills.get(student) * (1.0f - requiredSkillsWeight);
                }

                if(weight >= requiredSkillsWeight) {
                    Match match = new Match();
                    match.setStudent(student);
                    match.setJob(posting);
                    match.setMatchStrength(weight);

                    matches.add(match);
                }
            }

            matchDAO.save(matches);
        });
    }

    /**
     * Gets all the students with at least one skill on the list of skills and counts how many skills that student has
     *
     * @param skills The list of skills to look for students with
     * @return A map from students who have at least one skill on the list to the number of skills on the list they have
     */
    Map<Student, Integer> countStudentsWithSkillInList(final Iterable<Skill> skills) {
        final Map<Student, Integer> skillsCount = new HashMap<>();
        for(Skill skill : skills) {
            Set<Student> studentsWithSkill = studentDAO.findAllBySkillsContains(skill);

            for(Student s : studentsWithSkill) {
                Integer val = 0;
                if(skillsCount.containsKey(s)) {
                    val = skillsCount.get(s);
                }

                skillsCount.put(s, val + 1);
            }
        }

        return skillsCount;
    }

    /**
     * Builds the list of matches between students and potential jobs for that student
     *
     * @param student The student to generate matches for
     * @param matchedSkillsCountMap A count of how many skills the student has in common with each job posting
     * @return A list of all the Match objects that could be generated
     */
    List<Match> buildMatchesList(final Student student, final Map<JobPosting, MatchedSkillsCount> matchedSkillsCountMap) {
        final List<Match> matches = new ArrayList<>();
        for(JobPosting posting : matchedSkillsCountMap.keySet()) {
            MatchedSkillsCount matchedSkillsCount = matchedSkillsCountMap.get(posting);
            float numRequiredSkills = posting.getRequiredSkills().size();
            float weight = matchedSkillsCount.requiredSkillsCount * requiredSkillsWeight / numRequiredSkills;
            float numRecommendedSkills = posting.getNiceToHaveSkills().size();
            if(numRecommendedSkills > 0) {
                weight += matchedSkillsCount.recommendedSkillsCount * (1.0f - requiredSkillsWeight) / numRecommendedSkills;
            }

            Match match = new Match();
            match.setMatchStrength(weight);
            match.setJob(posting);
            match.setStudent(student);
            match.setApplicationStatus(Match.ApplicationStatus.NEW);
            match.setCurrentPhase(Match.CurrentPhase.NONE);
            matches.add(match);
        }
        return matches;
    }

    static class MatchedSkillsCount {
        int requiredSkillsCount = 0;
        int recommendedSkillsCount = 0;
    }
}

