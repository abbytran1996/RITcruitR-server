package com.avalanche.tmcs.matching;

import com.avalanche.tmcs.CompanyService;
import com.avalanche.tmcs.company.Company;
import com.avalanche.tmcs.company.CompanyDAO;
import com.avalanche.tmcs.job_posting.JobPosting;
import com.avalanche.tmcs.job_posting.JobPostingDAO;
import com.avalanche.tmcs.students.Student;
import com.avalanche.tmcs.students.StudentDAO;
import com.avalanche.tmcs.JobService;
import com.google.cloud.talent.v4beta1.*;
import com.google.cloud.talent.v4beta1.SearchJobsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Matches students and job postings
 *
 * @author David Dubois
 * @since 16-Apr-17.
 * @author Abigail My Tran
 */
@Service
public class MatchingService {

    private MatchDAO matchDAO;
    private StudentDAO studentDAO;
    private JobPostingDAO jobPostingDAO;
    private CompanyDAO companyDAO;

    private final static float REQUIRED_SKILL_WEIGHT = 0.8f;

    @Autowired
    public MatchingService(MatchDAO matchDAO, StudentDAO studentDAO, JobPostingDAO jobPostingDAO, CompanyDAO companyDAO) {
        this.matchDAO = matchDAO;
        this.studentDAO = studentDAO;
        this.jobPostingDAO = jobPostingDAO;
        this.companyDAO = companyDAO;
    }

    /**
     * Registers the given student with the matching student
     * <p>This calculates the best matches for this student</p>
     * @param student The student to register
     */
    public void registerStudent(final Student student) {
        final List<Match> oldMatches = matchDAO.findAllByStudent(student);
        //This is the old way of matching
        //List<Match> newMatches = generateMatchesForStudent(student);
        List<Match> newMatches = generateMatchesForStudentFromGoogleAPI(student);
        newMatches = deduplicateMatchListPreservingMatchStatus(newMatches, oldMatches);
        resetAllMatchesForStudent(student, newMatches);
    }

    /**
     * Registers the given job posting with the matching service
     * <p>This calculates the best matches for this job posting</p>
     * @param posting The job posting to register
     */
    //TODO: Use Google Cloud Talent Solution to implement matching for this
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

    public List<Match> generateMatchesForStudentFromGoogleAPI(final Student student) {
        //TODO: These credentials need to be stored in a separated file
        String PROJECT_ID = "recruitrtest-256719";
        String TENANT_ID = "075e3c6b-df00-0000-0000-00fbd63c7ae0";

        //Creating query string
        // 1.Skills => query strings
        List<String> skills = new ArrayList<>();
        if (student.getSkills().isEmpty()) {
            return new ArrayList();
        }
        for (Skill s : student.getSkills()) {
            skills.add(s.getName());
        }
        String skillStr = String.join(", ", skills);
        String query = skillStr;

        //2.Industries -> currently not used
        List<String> industries = new ArrayList<>();
        for (Industry i : student.getPreferredIndustries()) {
            industries.add(i.getName());
        }
        String industryStr = String.join(", ", industries);

        //3.Major -> currently not used
        String majorStr = student.getMajor().getName();

        //4.Locations => Location filter
        List<String> locations = new ArrayList<>();
        locations.addAll(student.getPreferredLocations());
        String locationStr = String.join(", ", student.getPreferredLocations());

        //5. Company size
        List<String> sizes = new ArrayList<>();
        for (Company.Size s : student.getPreferredCompanySizes()) {
            sizes.add(s.toString());
        }
        String sizeStr = String.join(", ", sizes);

        List<Match> matches = new ArrayList<>();
        try {
            List<SearchJobsResponse.MatchingJob> jobResults = JobService.searchJobsGoogleAPI(PROJECT_ID, query, locations, sizes);
            for (SearchJobsResponse.MatchingJob r : jobResults) {

                Job j = r.getJob();

                //Get companies' size and industries
                String companyNameGoogleAPI = j.getCompany();
                com.google.cloud.talent.v4beta1.Company companyGoogleAPI = CompanyService.getCompany(companyNameGoogleAPI);
                if (companyGoogleAPI == null) {
                    continue;
                }
                String companyId = companyGoogleAPI.getExternalId();

                Company company = companyDAO.findOne(Long.valueOf(companyId));
                if (company == null) {
                    continue;
                }
                String size = company.getSize() != null ? company.getSize().name() : "";
                Set<Industry> companyIndustries =  company.getIndustries();

                String companyIndustriesStr = "";
                for (Industry i : companyIndustries) {
                    companyIndustriesStr += i.getName() + ", ";
                }
                companyIndustriesStr = companyIndustriesStr.substring(0, companyIndustriesStr.length() - 2);


                JobPosting jp = jobPostingDAO.findOne(Long.parseLong(j.getRequisitionId()));
                if (jp == null) {
                    continue;
                }

                Set<Skill> recommendedSkills = jp.getRecommendedSkills();

                //Check Required Skills
                List<String> requiredSkills = new ArrayList<>();
                for (Skill s : jp.getRequiredSkills()) {
                    requiredSkills.add(s.getName());
                }
                boolean gotAllRequiredSkills = true;
                for (String s : requiredSkills) {
                    if (!skills.contains(s)) {
                        gotAllRequiredSkills = false;
                    }
                }
                if (gotAllRequiredSkills == false) {
                    continue;
                }

                //Recommended Skill
                String recommendedSkillsStr = "";
                for (Skill s : recommendedSkills) {
                    recommendedSkillsStr += s.getName() + ", ";
                }
                recommendedSkillsStr = recommendedSkillsStr.substring(0, recommendedSkillsStr.length() - 2);

                //Calculate all scores
                int recommendedSkillsScore = LockMatch.lock_match(recommendedSkillsStr, skillStr);
                int locationScore = LockMatch.lock_match(j.getAddresses(0), locationStr);
                int sizeScore = LockMatch.lock_match(size, sizeStr);
                //int industriesScore = LockMatch.lock_match(companyIndustriesStr, industryStr);



                float avgMatchScore = (float) ((recommendedSkillsScore + locationScore + sizeScore)/3);
                if (avgMatchScore < jp.getMatchThreshold()) {
                    continue;
                }
                Match newMatch = new Match();
                newMatch.setJob(jp);
                newMatch.setStudent(student);
                newMatch.setCurrentPhase(Match.CurrentPhase.PROBLEM_WAITING_FOR_STUDENT);
                newMatch.setApplicationStatus(Match.ApplicationStatus.NEW);
                newMatch.setMatchStrength(avgMatchScore/100);
                matches.add(newMatch);
            }

            Collections.sort(matches, Collections.reverseOrder());
            return matches;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matches;
    }
    public List<Match> generateMatchesForStudent(final Student student) {
        // create new matches for a student from all currently active jobs
        List<Match> matchesToReturn = jobPostingDAO.findAllByStatus(JobPosting.Status.ACTIVE).parallelStream()
                .map(job -> generateMatchForStudentAndJob(student, job))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        List<Match> matchesToReturnFromGoogleAPI = new ArrayList<>();


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

    //TODO: Look at this function
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
        double recommendedSkillsPercentage = calculateAndStoreMatchedRecommendedSkills(job.getRecommendedSkills(), studentSkills, newMatch);
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
        newMatch.setMatchedRecommendedSkills(matchedRecommendedSkills);
        return matchedRecommendedSkills.size()/(1.00f * recSkills.size());
    }

    private static List<Match> deduplicateMatchListPreservingMatchStatus(List<Match> newMatches, List<Match> oldMatches){
        // preserve the ApplicationStatus and CurrentPhase of the oldMatch when replacing it with its newer one
        for (Match old : oldMatches) {
            for (Match n : newMatches) {
                if (old.getJob().getId() == n.getJob().getId()) {
                    int dupedNewMatchLocation = newMatches.indexOf(n);
                    if(dupedNewMatchLocation != -1){ // -1 catches any mistaken duplicates
                        Match newMatch = newMatches.get(dupedNewMatchLocation);
                        newMatch.setApplicationStatus(old.getApplicationStatus());
                        newMatch.setCurrentPhase(old.getCurrentPhase());
                        newMatches.set(dupedNewMatchLocation, newMatch);
                    }
                }
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

    public static Set<Industry> getIndustrySetIntersection(Set<Industry> src, Set<Industry> cmp){
        // default return to empty set
        Set<Industry> cmpInSource = new HashSet<>();
        if(src == null || cmp == null){ return cmpInSource; }

        // populate
        cmpInSource.addAll(src);
        cmpInSource.retainAll(cmp);
        return cmpInSource;
    }
}

