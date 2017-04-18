package com.avalanche.tmcs.auth;

/**
 * @author David Dubois
 * @since 17-Apr-17.
 */
public interface UserService {
    void save(User user);
    User findByUsername(String username);
}
