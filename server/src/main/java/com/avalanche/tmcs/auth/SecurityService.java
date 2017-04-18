package com.avalanche.tmcs.auth;

/**
 * @author David Dubois
 * @since 17-Apr-17.
 */
public interface SecurityService {
    String findLoggedInUsername();

    void login(String username, String password);
}
