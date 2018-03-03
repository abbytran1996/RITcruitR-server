package com.avalanche.tmcs.job_posting;

import com.avalanche.tmcs.recruiter.Recruiter;
import com.avalanche.tmcs.matching.Skill;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Maxwell Hadley
 * @since 4/20/17
 */
public interface JobPostingDAO extends CrudRepository<JobPosting, Long> {
    List<JobPosting> findAllByRecruiter(Recruiter recruiter);

    List<JobPosting> findAllByImportantSkillsContains(Skill skill);

    List<JobPosting> findAllByNicetohaveSkillsContains(Skill skill);

}
