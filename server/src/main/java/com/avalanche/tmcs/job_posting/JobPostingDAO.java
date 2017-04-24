package com.avalanche.tmcs.job_posting;

import com.avalanche.tmcs.matching.Skill;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * @author Maxwell Hadley
 * @since 4/20/17
 */
public interface JobPostingDAO extends CrudRepository<JobPosting, Long> {
    //Recuriter findAllByRecruiter(Recruiter recruiter)

    Set<JobPosting> findAllByRequiredSkillsContains(Skill skill);

    Set<JobPosting> findAllByRecommendedSkillsContains(Skill skill);
}
