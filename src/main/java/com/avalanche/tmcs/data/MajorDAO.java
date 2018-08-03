package com.avalanche.tmcs.data;

import org.springframework.data.repository.CrudRepository;

public interface MajorDAO extends CrudRepository<Major, Long> {

    Iterable<Major> findAllOrOrderByCount();
}
