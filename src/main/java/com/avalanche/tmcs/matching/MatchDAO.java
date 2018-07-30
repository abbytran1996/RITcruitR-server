package com.avalanche.tmcs.matching;

import com.avalanche.tmcs.job_posting.JobPosting;
import com.avalanche.tmcs.students.Student;
import com.avalanche.tmcs.matching.Match;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

/**
 * @author David Dubois
 * @since 16-Apr-17.
 */
@Transactional
public interface MatchDAO extends CrudRepository<Match, Long> {
    List<Match> findAllByStudent(Student student);

    List<Match> findAllByJobAndApplicationStatus(JobPosting job, Match.ApplicationStatus status);

    List<Match> findAllByJobAndCurrentPhase(JobPosting job, Match.CurrentPhase currentPhase);

    List<Match> findAllByStudentAndCurrentPhase(Student student, Match.CurrentPhase currentPhase);

    List<Match> findAllByJob(JobPosting job);

    long countAllByJobAndCurrentPhase(JobPosting job, Match.CurrentPhase currentPhase);
    
    long countAllByJob(JobPosting job);
    
    long countAllByStudentAndCurrentPhase(Student student, Match.CurrentPhase currentPhase);
    
    long countAllByStudent(Student student);
}
