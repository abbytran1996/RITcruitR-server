package com.avalanche.tmcs.auth;

/**
 * An exception for when registration fails
 *
 * @author David Dubois
 * @since 17-Apr-17.
 */
public class RegistrationException extends RuntimeException {
    public RegistrationException(String msg) {
        super(msg);
    }
}
