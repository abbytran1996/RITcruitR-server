package com.avalanche.tmcs.job_posting;

import com.avalanche.tmcs.Recruiter.Recruiter;
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
    List<JobPosting> findAllByRecruiter(Recruiter recruiter);

    List<JobPosting> findAllByImportantSkillsContains(Skill skill);

    List<JobPosting> findAllByNicetohaveSkillsContains(Skill skill);

}
