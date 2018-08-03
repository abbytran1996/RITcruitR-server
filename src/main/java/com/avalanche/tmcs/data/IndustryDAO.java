package com.avalanche.tmcs.data;

import org.springframework.data.repository.CrudRepository;

public interface IndustryDAO extends CrudRepository<Industry, Long> {

    Iterable<Industry> findAllOrOrderByCount();
}
