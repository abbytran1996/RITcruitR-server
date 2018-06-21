package com.avalanche.tmcs.job_posting;

import com.avalanche.tmcs.company.Company;
import com.avalanche.tmcs.company.CompanyDAO;
import com.avalanche.tmcs.matching.Skill;
import com.avalanche.tmcs.recruiter.Recruiter;
import com.avalanche.tmcs.matching.MatchingService;
import com.avalanche.tmcs.recruiter.RecruiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private RecruiterRepository recruiterRepo;
    private CompanyDAO companyDAO;

    private MatchingService matchingService;

    @Autowired
    public JobPostingController(JobPostingDAO jobPostingDAO, MatchingService matchingService, RecruiterRepository repo, CompanyDAO companyDAO){
        this.jobPostingDAO = jobPostingDAO;
        this.matchingService = matchingService;
        this.recruiterRepo = repo;
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
    public ResponseEntity<JobPosting> addJobPosting(@PathVariable long company_id, @RequestBody NewJobPosting newJobPosting){
        Company company = companyDAO.findOne(company_id);
        Recruiter recruiter = recruiterRepo.findOne(newJobPosting.getRecruiterId());

        // Company or recruiter couldn't be found
        if (company == null || recruiter == null) {
            return ResponseEntity.notFound().build();
        }
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
    
    public boolean addPortfoliumJob(long company_id, JobPosting jobPosting, int portfolium_id) {
    	Company company = companyDAO.findOne(company_id);
        if (company == null) {
            return false;
        } else {
	    	jobPosting.setPortfoliumId(portfolium_id);
	    	jobPosting.setStatus(JobPosting.Status.NEEDS_DETAILING.toInt());
	    	jobPostingDAO.save(jobPosting);
	    	matchingService.registerJobPosting(jobPosting);
	    	return true;
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
        jobPosting.setNiceToHaveSkillsWeight(updatedJobPosting.getNiceToHaveSkillsWeight());
        jobPosting.setMinGPA(updatedJobPosting.getMinGPA());
        jobPosting.setHasWorkExperience(updatedJobPosting.getHasWorkExperience());
        jobPosting.setMatchThreshold(updatedJobPosting.getMatchThreshold());
        jobPosting.setDuration(updatedJobPosting.getDuration());
        jobPosting.setProblemStatement(updatedJobPosting.getProblemStatement());
        jobPosting.setVideo(updatedJobPosting.getVideo());
        jobPosting.setPresentationLinks(updatedJobPosting.getPresentationLinks());
        jobPostingDAO.save(jobPosting);
        return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * DELETE JOB [DELETE]                                                                                          *
    // ================================================================================================================
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteJobPosting(@PathVariable long id){
        JobPosting toDelete = jobPostingDAO.findOne(id);
        toDelete.setStatus(JobPosting.Status.DELETED.toInt());
        jobPostingDAO.save(toDelete);
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
    // * GET OPEN JOBS BY COMPANY [GET]                                                                               *
    // ================================================================================================================
    @RequestMapping(value = "/company/{company_id}/open", method=RequestMethod.GET)
    public ResponseEntity<List<JobPosting>> getOpenJobPostingsByCompany(@PathVariable long company_id){
        Company companyWithID = new Company();
        companyWithID.setId(company_id);

        List<JobPosting> jobPostings = jobPostingDAO.findAllByCompanyAndStatus(
                companyWithID,
                JobPosting.Status.OPEN.toInt()
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
                JobPosting.Status.FULFILLED.toInt()
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
                JobPosting.Status.DELETED.toInt()
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
    public void fulfillJobPosting(@PathVariable long id){
        JobPosting toFulfill = jobPostingDAO.findOne(id);
        toFulfill.setStatus(JobPosting.Status.FULFILLED.toInt());
        jobPostingDAO.save(toFulfill);
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
        posting.setNiceToHaveSkills(skills);
        jobPostingDAO.save(posting);
        matchingService.registerJobPosting(posting);
        return ResponseEntity.ok(posting);
    }
}
