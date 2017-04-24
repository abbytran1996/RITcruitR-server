package com.avalanche.tmcs.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * @author David Dubois
 * @since 17-Apr-17.
 */
@Transactional
public interface UserDAO extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
