package com.avalanche.tmcs.data;

import org.springframework.data.repository.CrudRepository;

public interface UniversityDAO extends CrudRepository<University, Long> {
    Iterable<University> findAllOrOrderByCount();
}
