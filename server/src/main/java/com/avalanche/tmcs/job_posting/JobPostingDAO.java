package com.avalanche.tmcs.job_posting;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Maxwell Hadley
 * @since 4/20/17
 */
public interface JobPostingDAO extends CrudRepository<JobPosting, Long> {
    //Recuriter findAllByRecruiter(Recruiter recruiter)
}
