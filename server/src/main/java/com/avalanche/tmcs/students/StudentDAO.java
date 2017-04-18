package com.avalanche.tmcs.students;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Provides an interface to manipulate the Student database table
 *
 * @author David Dubois
 * @since 5-Apr-2017
 */
@Transactional
public interface StudentDAO extends CrudRepository<Student, Long> {
    Optional<Student> findById(long id);
}
