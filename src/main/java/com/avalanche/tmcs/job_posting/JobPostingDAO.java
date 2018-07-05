package com.avalanche.tmcs.job_posting;

import com.avalanche.tmcs.company.Company;
import com.avalanche.tmcs.recruiter.Recruiter;
import com.avalanche.tmcs.matching.Skill;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Maxwell Hadley
 * @since 4/20/17
 */
public interface JobPostingDAO extends CrudRepository<JobPosting, Long> {
    List<JobPosting> findAllByCompany(Company company);

    List<JobPosting> findAllByStatus(int status);

    List<JobPosting> findAllByCompanyAndStatus(Company company, int status);

    List<JobPosting> findAllByRecruiter(Recruiter recruiter);

    List<JobPosting> findAllByRequiredSkillsContains(Skill skill);

    List<JobPosting> findAllByNiceToHaveSkillsContains(Skill skill);

}
