package com.avalanche.tmcs.job_posting;

import com.avalanche.tmcs.matching.Skill;
import jdk.nashorn.internal.scripts.JO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

/**
 * @author Maxwell Hadley
 * @since 4/20/17
 */
public interface JobPostingDAO extends CrudRepository<JobPosting, Long> {
    //Recuriter findAllByRecruiter(Recruiter recruiter)

    List<JobPosting> findAllByRequiredSkillsContains(Skill skill);

    List<JobPosting> findAllByRecommendedSkillsContains(Skill skill);

}
