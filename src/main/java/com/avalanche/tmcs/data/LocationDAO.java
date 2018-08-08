package com.avalanche.tmcs.data;

import org.springframework.data.repository.CrudRepository;

public interface LocationDAO extends CrudRepository<Location, Long> {

    Iterable<Location> findAllOrOrderByCount();
    Location findFirstByName(String name);
}
