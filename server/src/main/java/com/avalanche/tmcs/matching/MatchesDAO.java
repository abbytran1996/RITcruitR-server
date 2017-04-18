package com.avalanche.tmcs.matching;

import com.avalanche.tmcs.students.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author David Dubois
 * @since 16-Apr-17.
 */
public interface MatchesDAO extends CrudRepository<Match, Long> {
    List<Match> findAllByStudent(Student student);
}
