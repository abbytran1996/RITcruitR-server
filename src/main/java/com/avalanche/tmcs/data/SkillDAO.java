package com.avalanche.tmcs.data;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Max
 * @since 4/18/17
 */
public interface SkillDAO extends CrudRepository<Skill, Long> {
    Skill findByName(String name);
    Iterable<Skill> findAllOrOrderByCount();
}
