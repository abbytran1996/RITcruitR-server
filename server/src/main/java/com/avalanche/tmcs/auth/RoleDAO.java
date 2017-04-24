package com.avalanche.tmcs.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * @author David Dubois
 * @since 17-Apr-17.
 */
@Transactional
public interface RoleDAO extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
