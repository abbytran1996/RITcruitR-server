package com.avalanche.tmcs.matching;

import com.avalanche.tmcs.job_posting.JobPosting;
import com.avalanche.tmcs.job_posting.JobPostingDAO;
import com.avalanche.tmcs.students.Student;
import com.avalanche.tmcs.students.StudentDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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
        final List<Match> newMatches = generateMatchesForStudent(student);
        matchDAO.save(dedupeMatchListPreservingMatchStatus(newMatches, oldMatches));
    }

    /**
     * Registers the given job posting with the matching service
     * <p>This calculates the best matches for this job posting</p>
     * @param posting The job posting to register
     */
    public void registerJobPosting(final JobPosting posting) {
        final List<Match> oldMatches = matchDAO.findAllByJob(posting);
        final List<Match> newMatches = generateMatchesForJob(posting);
        matchDAO.save(dedupeMatchListPreservingMatchStatus(newMatches, oldMatches));
    }

    public List<Match> generateMatchesForStudent(final Student student) {
        // create new matches for a student from all currently active jobs
        List<Match> matchesToReturn = jobPostingDAO.findAllByStatus(JobPosting.Status.ACTIVE.toInt()).parallelStream()
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


    public static Optional<Match> generateMatchForStudentAndJob(final Student student, final JobPosting job){
        Set<Skill> studentSkills = student.getSkills();
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

        double jobFilterWeight = job.calculateJobFiltersWeight();
        double jobFilterScore = job.calculateJobFiltersScore(student);

        double studentPreferencesWeight = student.calculateStudentPreferencesWeight();
        double studentPreferencesScore = student.calculateStudentPreferencesScore(job);

        double normalizedWeightDenominator = REQUIRED_SKILL_WEIGHT+recommendedSkillsWeight+jobFilterWeight+studentPreferencesWeight;
        double matchScore = (requiredSkillsScore+recommendedSkillsScore+jobFilterScore+studentPreferencesScore) /
                (1.0*normalizedWeightDenominator);

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

    private static List<Match> dedupeMatchListPreservingMatchStatus(List<Match> newMatches, List<Match> oldMatches){
        // get list of duplicate matches
        List<Match> duplicateMatches = oldMatches.parallelStream()
                .filter(newMatches::contains)
                .collect(Collectors.toList());

        // replace the duplicated match in the new list to the old one
        int dupedNewMatchLocation;
        for(Match duplicate: duplicateMatches){
            dupedNewMatchLocation = newMatches.indexOf(duplicate);
            if(dupedNewMatchLocation != -1){
                newMatches.set(dupedNewMatchLocation, duplicate);
            }
        }
        return newMatches;
    }
}

