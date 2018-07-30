package com.avalanche.tmcs.students;

import com.avalanche.tmcs.matching.Skill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Provides an interface to manipulate the Student database table
 *
 * @author David Dubois
 * @since 5-Apr-2017
 */
@Transactional
public interface StudentDAO extends CrudRepository<Student, Long> {
    Set<Student> findAllBySkillsContains(Skill skill);
    Set<Student> findAll();

    Student findByEmail(String email);
}
