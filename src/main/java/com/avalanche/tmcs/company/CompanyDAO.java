package com.avalanche.tmcs.company;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Provides an interface to manipulate the Company database table
 *
 * Created by Zane Grasso
 * Created on 4/20/17.
 */
@Transactional
public interface CompanyDAO extends CrudRepository<Company, Long>{

    Company findByEmailSuffix (String emailSuffix);
    Company findByCompanyName(String companyName);
}
