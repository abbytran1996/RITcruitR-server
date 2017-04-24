package com.avalanche.tmcs.matching;

import com.avalanche.tmcs.job_posting.JobPosting;
import com.avalanche.tmcs.job_posting.JobPostingDAO;
import com.avalanche.tmcs.students.Student;
import com.avalanche.tmcs.students.StudentDAO;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
    void resgisterStudent(final Student student) {
        executor.execute(() -> {
            final Map<JobPosting, MatchedSkillsCount> matchedSkillsCountMap = new HashMap<>();
            for(Skill skill : student.getSkills()) {
                Set<JobPosting> postingsWithAtLeastOneRequiredSkill = jobPostingDAO.findAllByRequiredSkillsContains(skill);
                for(JobPosting posting : postingsWithAtLeastOneRequiredSkill) {
                    if(!matchedSkillsCountMap.containsKey(posting)) {
                        matchedSkillsCountMap.put(posting, new MatchedSkillsCount());
                    }
                    matchedSkillsCountMap.get(posting).requiredSkillsCount += 1;
                }

                Set<JobPosting> postingsWithAtLeastOneRecommendedSkill = jobPostingDAO.findAllByRecommendedSkillsContains(skill);
                for(JobPosting posting : postingsWithAtLeastOneRecommendedSkill) {
                    if(!matchedSkillsCountMap.containsKey(posting)) {
                        matchedSkillsCountMap.put(posting, new MatchedSkillsCount());
                    }
                    matchedSkillsCountMap.get(posting).recommendedSkillsCount += 1;
                }
            }

            final List<Match> matches = new ArrayList<>();
            for(JobPosting posting : matchedSkillsCountMap.keySet()) {
                MatchedSkillsCount matchedSkillsCount = matchedSkillsCountMap.get(posting);
                float weight = matchedSkillsCount.requiredSkillsCount * requiredSkillsWeight + matchedSkillsCount.recommendedSkillsCount * (1.0f - requiredSkillsWeight);

                Match match = new Match();
                match.setMatchStrength(weight);
                match.setJob(posting);
                match.setStudent(student);
                matches.add(match);
            }

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
    void registerJobPosting(final JobPosting posting) {
        executor.execute(() -> {
            final Map<Student, Integer> numberOfMatchedRequiredSkills = countStudentsWithSkillInList(posting.getRequiredSkills());
            final Map<Student, Integer> numberOfMatchedRecommendedSkills = countStudentsWithSkillInList(posting.getRecommendedSkills());

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

    private Map<Student, Integer> countStudentsWithSkillInList(final Iterable<Skill> skills) {
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

    private class MatchedSkillsCount {
        Integer requiredSkillsCount = 0;
        Integer recommendedSkillsCount = 0;
    }
}

