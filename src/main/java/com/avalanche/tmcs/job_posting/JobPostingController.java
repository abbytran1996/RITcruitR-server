package com.avalanche.tmcs.job_posting;

import com.avalanche.tmcs.recruiter.Recruiter;
import com.avalanche.tmcs.matching.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Maxwell Hadley
 * @since 4/20/17
 */
@RestController
@RequestMapping("/jobposting")
public class JobPostingController {

    private JobPostingDAO jobPostingDAO;

    private MatchingService matchingService;

    @Autowired
    public JobPostingController(JobPostingDAO jobPostingDAO, MatchingService matchingService){
        this.jobPostingDAO = jobPostingDAO;
        this.matchingService = matchingService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<JobPosting> getJobPosting(@PathVariable long id){
        return ResponseEntity.ok(jobPostingDAO.findOne(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> editJobPosting(@PathVariable long id, @RequestBody JobPosting updatedJobPosting) {
        JobPosting jobPosting = jobPostingDAO.findOne(id);
        jobPosting.setPositionTitle(updatedJobPosting.getPositionTitle());
        jobPosting.setLocation(updatedJobPosting.getLocation());
        jobPosting.setDescription(updatedJobPosting.getDescription());
        jobPosting.setMinGPA(updatedJobPosting.getMinGPA());
        //TODO add hasWorkExperience?
        jobPosting.setPhaseTimeout(updatedJobPosting.getPhaseTimeout());
        jobPosting.setProblemStatement(updatedJobPosting.getProblemStatement());
        jobPosting.setUrl(updatedJobPosting.getUrl());
        jobPostingDAO.save(jobPosting);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/fulfilled/{id}", method = RequestMethod.POST)
    public void fulfillJobPosting(@PathVariable long id){
        JobPosting toFulfill = jobPostingDAO.findOne(id);
        toFulfill.setStatus(JobPosting.Status.FULFILLED.toInt());
        jobPostingDAO.save(toFulfill);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteJobPosting(@PathVariable long id){
        JobPosting toDelete = jobPostingDAO.findOne(id);
        toDelete.setStatus(JobPosting.Status.DELETED.toInt());
        jobPostingDAO.save(toDelete);
    }

    @RequestMapping(value = "/create", method=RequestMethod.POST)
    public void createJobPosting(@RequestBody JobPosting newJobPosting){
        JobPosting newPosting = jobPostingDAO.save(newJobPosting);
        matchingService.registerJobPosting(newPosting);
    }

    @RequestMapping(value = "/recruiter/{id}", method=RequestMethod.GET)
    public ResponseEntity<List<JobPosting>> getJobPostingsByRecruiter(@PathVariable long id){
        Recruiter recruiterWithID = new Recruiter();
        recruiterWithID.setId(id);

        List<JobPosting> jobPostings = jobPostingDAO.findAllByRecruiter(recruiterWithID);

        return ResponseEntity.ok(jobPostings);
    }

}
