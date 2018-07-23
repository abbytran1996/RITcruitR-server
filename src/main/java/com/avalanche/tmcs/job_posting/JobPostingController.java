package com.avalanche.tmcs.job_posting;

import com.avalanche.tmcs.company.Company;
import com.avalanche.tmcs.company.CompanyDAO;
import com.avalanche.tmcs.matching.Skill;
import com.avalanche.tmcs.recruiter.Recruiter;
import com.avalanche.tmcs.matching.Match;
import com.avalanche.tmcs.matching.MatchingService;
import com.avalanche.tmcs.recruiter.RecruiterDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;

/**
 * @author Maxwell Hadley
 * @since 4/20/17
 */
@RestController
@RequestMapping("/jobposting")
public class JobPostingController {

    private JobPostingDAO jobPostingDAO;
    private RecruiterDAO recruiterRepo;
    private JobPresentationLinkDAO presentationLinkDAO;
    private CompanyDAO companyDAO;

    private MatchingService matchingService;

    @Autowired
    public JobPostingController(JobPostingDAO jobPostingDAO, JobPresentationLinkDAO presentationLinkDAO, MatchingService matchingService, RecruiterDAO recruiterDAO, CompanyDAO companyDAO){
        this.jobPostingDAO = jobPostingDAO;
        this.presentationLinkDAO = presentationLinkDAO;
        this.matchingService = matchingService;
        this.recruiterRepo = recruiterDAO;
        this.companyDAO = companyDAO;
    }

    // ================================================================================================================
    // * GET JOB BY ID [GET]                                                                                          *
    // ================================================================================================================
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<JobPosting> getJobPosting(@PathVariable long id){
        return ResponseEntity.ok(jobPostingDAO.findOne(id));
    }

    // ================================================================================================================
    // * ADD NEW JOB [POST]                                                                                           *
    // ================================================================================================================
    @RequestMapping(value = "/{company_id}", method=RequestMethod.POST)
    public ResponseEntity<?> addJobPosting(@PathVariable long company_id, @RequestBody NewJobPosting newJobPosting){
        Company company = companyDAO.findOne(company_id);
        Recruiter recruiter = recruiterRepo.findOne(newJobPosting.getRecruiterId());

        // Company or recruiter couldn't be found
        if (company == null || recruiter == null) {
            return ResponseEntity.notFound().build();
        }

        // Company is not approved and can't post jobs
        else if (company.getStatus() != Company.Status.APPROVED.toInt()){
            return new ResponseEntity<String>(
                    "Company '" + company.getCompanyName() + "' is not authorized to post jobs",
                    HttpStatus.UNAUTHORIZED
            );
        }

        // Company exists and is authorized
        else {
            newJobPosting.setCompany(company);
            newJobPosting.setRecruiter(recruiter);
            JobPosting savedJobPosting = jobPostingDAO.save(newJobPosting.toJobPosting());

            // Create presentation links
            for (JobPresentationLink link : newJobPosting.getPresentationLinks()) {
                link.setJob(savedJobPosting);
            }
            savedJobPosting.setPresentationLinks(newJobPosting.getPresentationLinks());
            jobPostingDAO.save(savedJobPosting);

            matchingService.registerJobPosting(savedJobPosting);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedJobPosting.getId())
                    .toUri();

            return ResponseEntity.created(location).body(savedJobPosting);
        }
    }

    // ================================================================================================================
    // * UPDATE JOB [PUT]                                                                                             *
    // ================================================================================================================
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateJobPosting(@PathVariable long id, @RequestBody JobPosting updatedJobPosting) {
        JobPosting jobPosting = jobPostingDAO.findOne(id);
        jobPosting.setStatus(updatedJobPosting.getStatus());
        jobPosting.setPositionTitle(updatedJobPosting.getPositionTitle());
        jobPosting.setDescription(updatedJobPosting.getDescription());
        jobPosting.setLocations(updatedJobPosting.getLocations());
        jobPosting.setRecommendedSkillsWeight(updatedJobPosting.getRecommendedSkillsWeight());
        jobPosting.setMinGPA(updatedJobPosting.getMinGPA());
        jobPosting.setHasWorkExperience(updatedJobPosting.getHasWorkExperience());
        jobPosting.setMatchThreshold(updatedJobPosting.getMatchThreshold());
        jobPosting.setDuration(updatedJobPosting.getDuration());
        jobPosting.setProblemStatement(updatedJobPosting.getProblemStatement());
        jobPosting.setVideo(updatedJobPosting.getVideo());

        // Remove existing removed presentation links
        for (JobPresentationLink link : jobPosting.getPresentationLinks()) {
            if (!link.isInSet(updatedJobPosting.getPresentationLinks())) {
                jobPosting.getPresentationLinks().remove(link);
                presentationLinkDAO.delete(link);
            }
        }

        // Add new presentation links
        for (JobPresentationLink link : updatedJobPosting.getPresentationLinks()) {
            if (!link.isInSet(jobPosting.getPresentationLinks())) {
                link.setJob(jobPosting);
                jobPosting.getPresentationLinks().add(link);
            }
        }

        jobPostingDAO.save(jobPosting);
        return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * UPDATE JOB STATUS [PATCH]                                                                                             *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/status/{status}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateJobPostingStatus(@PathVariable long id, @PathVariable String status) {
        JobPosting jobPosting = jobPostingDAO.findOne(id);
        switch (status) {
        case "active":
        	int duration = jobPosting.getDuration();
        	jobPosting.setStatus(JobPosting.Status.ACTIVE.toInt());
        	jobPosting.setNumDaysRemaining(duration);
            break;
        case "inactive":
        	jobPosting.setStatus(JobPosting.Status.INACTIVE.toInt());
            break;
        case "archived":
        	jobPosting.setStatus(JobPosting.Status.ARCHIVED.toInt());
            break;
        case "needs_detailing":
        	jobPosting.setStatus(JobPosting.Status.NEEDS_DETAILING.toInt());
            break;
        default:
        	return ResponseEntity.badRequest().build();
        }
        jobPostingDAO.save(jobPosting);
        return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * DELETE JOB [DELETE]                                                                                          *
    // ================================================================================================================
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteJobPosting(@PathVariable long id){
        JobPosting toDelete = jobPostingDAO.findOne(id);
        toDelete.setStatus(JobPosting.Status.ARCHIVED.toInt());
        jobPostingDAO.save(toDelete);

        return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * GET JOBS BY COMPANY [GET]                                                                                    *
    // ================================================================================================================
    @RequestMapping(value = "/company/{company_id}", method=RequestMethod.GET)
    public ResponseEntity<List<JobPosting>> getJobPostingsByCompany(@PathVariable long company_id){
        Company companyWithID = new Company();
        companyWithID.setId(company_id);

        List<JobPosting> jobPostings = jobPostingDAO.findAllByCompany(companyWithID);

        return ResponseEntity.ok(jobPostings);
    }

    // ================================================================================================================
    // * GET JOBS BY COMPANY AND STATUS [GET]                                                                               *
    // ================================================================================================================
    @RequestMapping(value = "/company/{company_id}/status/{status}", method=RequestMethod.GET)
    public ResponseEntity<List<JobPosting>> getJobPostingsByCompanyAndStatus(@PathVariable long company_id, @PathVariable String status){
        Company companyWithID = new Company();
        companyWithID.setId(company_id);

        List<JobPosting> jobPostings;
        switch (status) {
        case "active":
        	jobPostings = jobPostingDAO.findAllByCompanyAndStatus(companyWithID, JobPosting.Status.ACTIVE.toInt());
            break;
        case "inactive":
        	jobPostings = jobPostingDAO.findAllByCompanyAndStatus(companyWithID, JobPosting.Status.INACTIVE.toInt());
            break;
        case "archived":
        	jobPostings = jobPostingDAO.findAllByCompanyAndStatus(companyWithID, JobPosting.Status.ARCHIVED.toInt());
            break;
        case "needs_detailing":
        	jobPostings = jobPostingDAO.findAllByCompanyAndStatus(companyWithID, JobPosting.Status.NEEDS_DETAILING.toInt());
            break;
        default:
            jobPostings = jobPostingDAO.findAllByCompany(companyWithID);
        }
        return ResponseEntity.ok(jobPostings);
    }

    // ================================================================================================================
    // * GET OPEN JOBS BY COMPANY [GET]                                                                               *
    // ================================================================================================================
    @RequestMapping(value = "/company/{company_id}/open", method=RequestMethod.GET)
    public ResponseEntity<List<JobPosting>> getOpenJobPostingsByCompany(@PathVariable long company_id){
        Company companyWithID = new Company();
        companyWithID.setId(company_id);

        List<JobPosting> jobPostings = jobPostingDAO.findAllByCompanyAndStatus(
                companyWithID,
                JobPosting.Status.ACTIVE.toInt()
        );

        return ResponseEntity.ok(jobPostings);
    }

    // ================================================================================================================
    // * GET FULFILLED JOBS BY COMPANY [GET]                                                                          *
    // ================================================================================================================
    @RequestMapping(value = "/company/{company_id}/fulfilled", method=RequestMethod.GET)
    public ResponseEntity<List<JobPosting>> getFulfilledJobPostingsByCompany(@PathVariable long company_id){
        Company companyWithID = new Company();
        companyWithID.setId(company_id);

        List<JobPosting> jobPostings = jobPostingDAO.findAllByCompanyAndStatus(
                companyWithID,
                JobPosting.Status.INACTIVE.toInt()
        );

        return ResponseEntity.ok(jobPostings);
    }

    // ================================================================================================================
    // * GET DELETED JOBS BY COMPANY [GET]                                                                            *
    // ================================================================================================================
    @RequestMapping(value = "/company/{company_id}/deleted", method=RequestMethod.GET)
    public ResponseEntity<List<JobPosting>> getDeletedJobPostingsByCompany(@PathVariable long company_id){
        Company companyWithID = new Company();
        companyWithID.setId(company_id);

        List<JobPosting> jobPostings = jobPostingDAO.findAllByCompanyAndStatus(
                companyWithID,
                JobPosting.Status.ARCHIVED.toInt()
        );

        return ResponseEntity.ok(jobPostings);
    }

    // ================================================================================================================
    // * GET JOBS BY RECRUITER [GET]                                                                                  *
    // ================================================================================================================
    @RequestMapping(value = "/recruiter/{id}", method=RequestMethod.GET)
    public ResponseEntity<List<JobPosting>> getJobPostingsByRecruiter(@PathVariable long id){
        Recruiter recruiterWithID = new Recruiter();
        recruiterWithID.setId(id);

        List<JobPosting> jobPostings = jobPostingDAO.findAllByRecruiter(recruiterWithID);

        return ResponseEntity.ok(jobPostings);
    }

    // ================================================================================================================
    // * FULFILL JOB [POST]                                                                                           *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/fulfill", method = RequestMethod.POST)
    public ResponseEntity<?> fulfillJobPosting(@PathVariable long id){
        JobPosting toFulfill = jobPostingDAO.findOne(id);
        toFulfill.setStatus(JobPosting.Status.INACTIVE.toInt());
        jobPostingDAO.save(toFulfill);

        return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * ADD REQUIRED SKILLS TO JOB [POST]                                                                            *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/requiredskills", method = RequestMethod.POST)
    public ResponseEntity<JobPosting> updateRequiredSkills(@PathVariable long id, @RequestBody Set<Skill> skills){
        JobPosting posting = jobPostingDAO.findOne(id);
        if(posting == null) {
            return ResponseEntity.notFound().build();
        }
        posting.setRequiredSkills(skills);
        jobPostingDAO.save(posting);
        matchingService.registerJobPosting(posting);
        return ResponseEntity.ok(posting);
    }

    // ================================================================================================================
    // * ADD NICE TO HAVE SKILLS TO JOB [POST]                                                                        *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/nicetohaveskills", method = RequestMethod.POST)
    public ResponseEntity<JobPosting> updateNiceToHaveSkills(@PathVariable long id, @RequestBody Set<Skill> skills){
        JobPosting posting = jobPostingDAO.findOne(id);
        if(posting == null) {
            return ResponseEntity.notFound().build();
        }
        posting.setRecommendedSkills(skills);
        jobPostingDAO.save(posting);
        matchingService.registerJobPosting(posting);
        return ResponseEntity.ok(posting);
    }
}
