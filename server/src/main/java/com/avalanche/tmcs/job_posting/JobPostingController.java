package com.avalanche.tmcs.job_posting;

import com.avalanche.tmcs.matching.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public JobPosting getJobPosting(@PathVariable long id){
        return jobPostingDAO.findOne(id);
    }

    @RequestMapping(value = "/fulfilled/{id}", method = RequestMethod.POST)
    public void fulfillJobPosting(@PathVariable long id){
        JobPosting toFulfill = jobPostingDAO.findOne(id);
        toFulfill.setStatus(Status.FULFILLED.toInt());
        jobPostingDAO.save(toFulfill);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteJobPosting(@PathVariable long id){
        JobPosting toDelete = jobPostingDAO.findOne(id);
        toDelete.setStatus(Status.DELETED.toInt());
        jobPostingDAO.save(toDelete);
    }

    @RequestMapping(value = "/create", method=RequestMethod.POST)
    public void createJobPosting(@RequestBody JobPosting newJobPosting){
        JobPosting newPosting = jobPostingDAO.save(newJobPosting);
        matchingService.registerJobPosting(newPosting);
    }

}
