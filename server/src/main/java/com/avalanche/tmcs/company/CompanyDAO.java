package com.avalanche.tmcs.company;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import javax.persistence.Id;

/**
 * Provides an interface to manipulate the Company database table
 *
 * Created by Zane Grasso
 * Created on 4/20/17.
 */
@Transactional
public interface CompanyDAO extends CrudRepository<Company, Long>{
    List<Company> findAllBy(Id id);
}
