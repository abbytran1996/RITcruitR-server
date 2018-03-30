package com.avalanche.tmcs.recruiter;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Created by John on 4/17/2017.
 */
@Transactional
public interface RecruiterRepository extends CrudRepository<Recruiter, Long> {
    Optional<Recruiter> findById(long id);

    Recruiter findByEmail(String email);

}
