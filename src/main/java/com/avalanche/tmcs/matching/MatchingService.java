package com.avalanche.tmcs.matching;

import com.avalanche.tmcs.job_posting.JobPosting;
import com.avalanche.tmcs.job_posting.JobPostingDAO;
import com.avalanche.tmcs.students.Student;
import com.avalanche.tmcs.students.StudentDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        final List<Match> oldMatches = matchDAO.findAllByStudent(student);
        List<Match> newMatches = generateMatchesForStudent(student);
        newMatches = deduplicateMatchListPreservingMatchStatus(newMatches, oldMatches);
        resetAllMatchesForStudent(student, newMatches);
    }

    /**
     * Registers the given job posting with the matching service
     * <p>This calculates the best matches for this job posting</p>
     * @param posting The job posting to register
     */
    public void registerJobPosting(final JobPosting posting) {
        final List<Match> oldMatches = matchDAO.findAllByJob(posting);
        List<Match> newMatches = generateMatchesForJob(posting);
        newMatches = deduplicateMatchListPreservingMatchStatus(newMatches, oldMatches);
        resetAllMatchesForJob(posting, newMatches);
    }

    public void reactivateMatchesForJob(JobPosting job){
        matchDAO.findAllByJob(job).stream()
                .map(Match::resetIfDeactivated)
                .forEach(matchDAO::save);
    }

    public void expireNonFinalMatchesForJob(JobPosting job){
        matchDAO.findAllByJob(job).parallelStream()
                .map(Match::deactivateIfNotFinal)
                .forEach(matchDAO::save);
    }

    public List<Match> generateMatchesForStudent(final Student student) {
        // create new matches for a student from all currently active jobs
        List<Match> matchesToReturn = jobPostingDAO.findAllByStatus(JobPosting.Status.ACTIVE).parallelStream()
                .map(job -> generateMatchForStudentAndJob(student, job))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        // re-add matches that are already in or past the FINAL stage (even though they weren't re-matched)
        final Set<Match> preexistingFinalMatches = matchDAO.findAllByStudent(student).parallelStream()
                .filter(match -> !matchesToReturn.contains(match)) // don't re-add duplicate matches
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

        // re-add matches that are already in or past the FINAL stage (even though they weren't re-matched)
        Set<Match> preexistingFinalMatches = matchDAO.findAllByJob(job).parallelStream()
                .filter(match -> !matchesToReturn.contains(match)) // don't re-add duplicate matches
                .filter(match -> match.getCurrentPhase() == Match.CurrentPhase.FINAL ||
                        match.getCurrentPhase() == Match.CurrentPhase.ARCHIVED)
                .collect(Collectors.toSet());
        matchesToReturn.addAll(preexistingFinalMatches);
        return matchesToReturn;
    }

    private void resetAllMatchesForJob(JobPosting job, List<Match> newMatchesForJob){
        matchDAO.deleteAllByJob(job);
        matchDAO.save(newMatchesForJob);
    }

    private void resetAllMatchesForStudent(Student student, List<Match> newMatchesForStudent){
        matchDAO.deleteAllByStudent(student);
        matchDAO.save(newMatchesForStudent);
    }

    public static Optional<Match> generateMatchForStudentAndJob(final Student student, final JobPosting job){
        if(student == null || !student.readyToMatch() ||
                job == null || !job.readyToMatch()) {
            return Optional.empty();
        }

        Match newMatch = new Match()
                .setJob(job)
                .setStudent(student);

        Set<Skill> studentSkills = student.getSkills();
        double requiredSkillsPercentage = calculateAndStoreMatchedRequiredSkills(job.getRequiredSkills(), studentSkills, newMatch);
        double requiredSkillsScore = REQUIRED_SKILL_WEIGHT * requiredSkillsPercentage;

        double recommendedSkillsWeight = job.getRecommendedSkillsWeight();
        double recommendedSkillsPercentage = calculateAndStoreMatchedRecommendedSkills(job.getRequiredSkills(), studentSkills, newMatch);
        double recommendedSkillsScore = recommendedSkillsWeight * recommendedSkillsPercentage;

        double jobFilterWeight = job.calculateJobFiltersWeight();
        double jobFilterScore = job.calculateJobFiltersScore(student);

        double studentPreferencesWeight = student.calculateStudentPreferencesWeight();
        double studentPreferencesScore = student.calculateStudentPreferencesScore(job, newMatch);

        double normalizedWeightDenominator = REQUIRED_SKILL_WEIGHT+recommendedSkillsWeight+jobFilterWeight+studentPreferencesWeight;
        double matchScore = (requiredSkillsScore+recommendedSkillsScore+jobFilterScore+studentPreferencesScore) /
                (1.0*normalizedWeightDenominator);

        if(matchScore >= job.getMatchThreshold()){
            newMatch.setMatchStrength((float) matchScore);
            newMatch.setCurrentPhase(Match.CurrentPhase.PROBLEM_WAITING_FOR_STUDENT);
            newMatch.setApplicationStatus(Match.ApplicationStatus.NEW);
            return Optional.of(newMatch);
        } else {
            return Optional.empty();
        }
    }

    private static double calculateAndStoreMatchedRequiredSkills(Set<Skill> reqSkills, Set<Skill> studentSkills, Match newMatch){
        if(studentSkills == null || studentSkills.isEmpty()){ return 0; }
        if(reqSkills == null || reqSkills.isEmpty()){ return 1.00; }
        Set<Skill> matchedRequiredSkills = getSkillSetIntersection(reqSkills, studentSkills);
        newMatch.setMatchedRequiredSkills(matchedRequiredSkills);
        return matchedRequiredSkills.size()/(1.00f * reqSkills.size());
    }

    private static double calculateAndStoreMatchedRecommendedSkills(Set<Skill> recSkills, Set<Skill> studentSkills, Match newMatch){
        if(studentSkills == null || studentSkills.isEmpty()){ return 0; }
        if(recSkills == null || recSkills.isEmpty()){ return 1.00; }
        Set<Skill> matchedRecommendedSkills = getSkillSetIntersection(recSkills, studentSkills);
        newMatch.setMatchedNiceToHaveSkills(matchedRecommendedSkills);
        return matchedRecommendedSkills.size()/(1.00f * recSkills.size());
    }

    private static List<Match> deduplicateMatchListPreservingMatchStatus(List<Match> newMatches, List<Match> oldMatches){
        // get list of duplicate matches
        List<Match> duplicateOldMatches = oldMatches.parallelStream()
                .filter(newMatches::contains)
                .collect(Collectors.toList());

        // preserve the ApplicationStatus and CurrentPhase of the oldMatch when replacing it with its newer one
        int dupedNewMatchLocation;
        for(Match oldDuplicate: duplicateOldMatches){
            dupedNewMatchLocation = newMatches.indexOf(oldDuplicate);
            if(dupedNewMatchLocation != -1){ // -1 catches any mistaken duplicates
                Match newMatch = newMatches.get(dupedNewMatchLocation);
                newMatch.setApplicationStatus(oldDuplicate.getApplicationStatus());
                newMatch.setCurrentPhase(oldDuplicate.getCurrentPhase());
                newMatches.set(dupedNewMatchLocation, newMatch);
            }
        }
        return newMatches;
    }

    private static Set<Skill> getSkillSetIntersection(Set<Skill> src, Set<Skill> cmp){
        // default return to empty set
        Set<Skill> cmpInSource = new HashSet<>();
        if(src == null || cmp == null){ return cmpInSource; }

        // populate
        cmpInSource.addAll(src);
        cmpInSource.retainAll(cmp);
        return cmpInSource;
    }

    public static Set<String> getStringSetIntersection(Set<String> src, Set<String> cmp){
        Set<String> toCompareInSrc = new HashSet<>();
        if(src == null || cmp == null){ return toCompareInSrc; }
        toCompareInSrc.addAll(src);
        toCompareInSrc.retainAll(cmp);
        return toCompareInSrc;
    }
}

