package com.avalanche.tmcs.job_posting;

import com.avalanche.tmcs.company.Company;
import com.avalanche.tmcs.recruiter.Recruiter;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Maxwell Hadley
 * @since 4/20/17
 */
public interface JobPostingDAO extends CrudRepository<JobPosting, Long> {
    List<JobPosting> findAllByCompany(Company company);

    List<JobPosting> findAllByStatus(JobPosting.Status status);

    List<JobPosting> findAllByCompanyAndStatus(Company company, JobPosting.Status status);

    List<JobPosting> findAllByRecruiter(Recruiter recruiter);

}
