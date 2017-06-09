package com.avalanche.tmcs.matching;

import com.avalanche.tmcs.job_posting.JobPosting;
import com.avalanche.tmcs.students.Student;
import com.avalanche.tmcs.matching.Match;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

/**
 * @author David Dubois
 * @since 16-Apr-17.
 */
public interface MatchDAO extends CrudRepository<Match, Long> {
    List<Match> findAllByStudent(Student student);

    List<Match> findAllByApplicationStatus(Match.ApplicationStatus status);

    List<Match> findAllByJobAndCurrentPhase(JobPosting job, Match.CurrentPhase currentPhase);

    List<Match> findAllByJob(JobPosting job);


}
