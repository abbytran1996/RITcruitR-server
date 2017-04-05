package com.avalanche.tmcs.models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Provides an interface to manipulate the Student database table
 *
 * @author David Dubois
 * @since 5-Apr-2017
 */
@Transactional
public interface StudentDAO extends CrudRepository<Student, Long> {
    Student findById(long id);
}
