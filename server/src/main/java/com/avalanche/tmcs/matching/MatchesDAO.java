package com.avalanche.tmcs.matching;

import com.avalanche.tmcs.students.StudentModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author David Dubois
 * @since 16-Apr-17.
 */
public interface MatchesDAO extends CrudRepository<MatchModel, Long> {
    List<MatchModel> findAllByStudent(StudentModel student);
}
