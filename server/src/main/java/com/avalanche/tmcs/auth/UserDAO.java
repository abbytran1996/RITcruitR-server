package com.avalanche.tmcs.auth;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author David Dubois
 * @since 17-Apr-17.
 */
public interface UserDAO extends JpaRepository<User, Long> {
    User findByUsername(String username);
}