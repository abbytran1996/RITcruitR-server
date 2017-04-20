package com.avalanche.tmcs.job_posting;

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

    @Autowired
    public JobPostingController(JobPostingDAO jobPostingDAO){
        this.jobPostingDAO = jobPostingDAO;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public JobPosting getJobPosting(@PathVariable long id){
        return jobPostingDAO.findOne(id);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteJobPosting(@PathVariable long id){
        jobPostingDAO.delete(id);
    }

    @RequestMapping(value = "/create", method=RequestMethod.POST)
    public void createJobPosting(@RequestBody JobPosting newJobPosting){
        jobPostingDAO.save(newJobPosting);
    }

}
