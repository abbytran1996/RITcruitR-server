package com.avalanche.tmcs.matching;

import com.avalanche.tmcs.students.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * @author Max
 * @since 4/18/17
 */
public interface SkillDAO extends CrudRepository<Skill, Long> {
    Skill findByName(String name);
}
