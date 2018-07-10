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
import java.util.stream.Collectors;

import static com.avalanche.tmcs.utils.SetUtilities.*;

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

    private Executor executor = Executors.newFixedThreadPool(10); //TODO replace with parallelStream
    private final static float REQUIRED_SKILL_WEIGHT = 0.8f;

    @Autowired
    public MatchingService(MatchDAO matchDAO, StudentDAO studentDAO, JobPostingDAO jobPostingDAO) {
        this.matchDAO = matchDAO;
        this.studentDAO = studentDAO;
        this.jobPostingDAO = jobPostingDAO;
    }

    /**
     * Registers the given student with the matching student
     * <p>This calculates the best matches for this student</p>
     * @param student The student to register
     */
    public void registerStudent(final Student student) {
        final List<Match> matches = generateMatchesForStudent(student);
        matchDAO.save(matches);
    }

    /**
     * Registers the given job posting with the matching service
     * <p>This calculates the best matches for this job posting</p>
     * @param posting The job posting to register
     */
    public void registerJobPosting(final JobPosting posting) {
        final List<Match> matches = generateMatchesForJob(posting);
        matchDAO.save(matches);
    }

    public List<Match> generateMatchesForStudent(final Student student) {
        // create new matches for a student from all currently active jobs
        List<Match> matchesToReturn = jobPostingDAO.findAllByStatus(JobPosting.Status.ACTIVE.toInt()).parallelStream()
                .map(job -> generateMatchForStudentAndJob(student, job))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        // re-add matches that are already in or past the FINAL stage
        final Set<Match> preexistingFinalMatches = matchDAO.findAllByStudent(student).parallelStream()
                .filter(match -> match.getCurrentPhase() == Match.CurrentPhase.FINAL ||
                        match.getCurrentPhase() == Match.CurrentPhase.ARCHIVED)
                .collect(Collectors.toSet());
        matchesToReturn.addAll(preexistingFinalMatches);
        return matchesToReturn;
    }

    public List<Match> generateMatchesForJob(final JobPosting job){
        // create new matches for a job from all current students
        List<Match> matchesToReturn = studentDAO.findAll().parallelStream()
                .map(student -> generateMatchForStudentAndJob(student, job))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        // re-add matches that are already in or past the FINAL stage
        Set<Match> preexistingFinalMatches = matchDAO.findAllByJob(job).parallelStream()
                .filter(match -> match.getCurrentPhase() == Match.CurrentPhase.FINAL ||
                        match.getCurrentPhase() == Match.CurrentPhase.ARCHIVED)
                .collect(Collectors.toSet());
        matchesToReturn.addAll(preexistingFinalMatches);
        return matchesToReturn;
    }


    public static Optional<Match> generateMatchForStudentAndJob(final Student student, final JobPosting job){
        Set<Skill> studentSkills = student.getSkills();
        double matchScore = 0;
        double requiredSkillsScore = weightedPercentageSetIntersection(
                job.getRequiredSkills(),
                studentSkills,
                REQUIRED_SKILL_WEIGHT
        );

        double recommendedSkillsWeight = job.getRecommendedSkillsWeight();
        double recommendedSkillsScore = weightedPercentageSetIntersection(
                job.getRecommendedSkills(),
                studentSkills,
                (float) recommendedSkillsWeight
        );

        double jobFilterWeight = job.getJobFiltersWeight();
        double jobFilterScore = job.calculateJobFiltersScore(student);

        double studentPreferencesWeight = student.getStudentPreferencesWeight();
        double studentPreferencesScore = student.calculateStudentPreferencesScore(job);


        double normalizedWeightDenominator = REQUIRED_SKILL_WEIGHT+recommendedSkillsWeight+jobFilterWeight+studentPreferencesWeight;
        matchScore = (requiredSkillsScore+recommendedSkillsScore+jobFilterScore+studentPreferencesScore) / normalizedWeightDenominator;

        if(matchScore >= job.getMatchThreshold()){
            Match newMatch = new Match()
                    .setJob(job)
                    .setStudent(student)
                    .setMatchStrength((float) matchScore);
            return Optional.of(newMatch);
        } else {
            return Optional.empty();
        }
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
        for (JobPosting posting : matchedSkillsCountMap.keySet()) {
            MatchedSkillsCount matchedSkillsCount = matchedSkillsCountMap.get(posting);
            double numRequiredSkills = posting.getRequiredSkills().size();
            double weight = matchedSkillsCount.requiredSkillsCount * REQUIRED_SKILL_WEIGHT / numRequiredSkills;
            int numRecommendedSkills = posting.getRecommendedSkills().size();
            if (numRecommendedSkills > 0) {
                weight += matchedSkillsCount.recommendedSkillsCount * (1.0f - REQUIRED_SKILL_WEIGHT) / numRecommendedSkills;
            }

            Match match = new Match();
            match.setMatchStrength((float) weight);
            match.setJob(posting);
            match.setStudent(student);
            match.setApplicationStatus(Match.ApplicationStatus.NEW);
            match.setCurrentPhase(Match.CurrentPhase.PROBLEM_WAITING_FOR_STUDENT);
            matches.add(match);
        }
        return matches;
    }

    /**
     * Gets all the students with at least one skill on the list of skills and counts how many skills that student has
     *
     * @param skills The list of skills to look for students with
     * @return A map from students who have at least one skill on the list to the number of skills on the list they have
     */
    Map<Student, Integer> countStudentsWithSkillInList(final Iterable<Skill> skills) {
        final Map<Student, Integer> skillsCount = new HashMap<>();
        for (Skill skill : skills) {
            Set<Student> studentsWithSkill = studentDAO.findAllBySkillsContains(skill);

            for (Student s : studentsWithSkill) {
                Integer val = 0;
                if (skillsCount.containsKey(s)) {
                    val = skillsCount.get(s);
                }

                skillsCount.put(s, val + 1);
            }
        }
        return skillsCount;
    }

    static class MatchedSkillsCount {
        int requiredSkillsCount = 0;
        int recommendedSkillsCount = 0;
    }

}

