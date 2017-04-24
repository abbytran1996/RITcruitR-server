package com.avalanche.tmcs.exceptions;

/**
 * Useful when a resource can't be found by the server
 *
 * @author David Dubois
 * @since 21-Apr-17.
 */
public class ResourceNotFound extends RuntimeException {
    public ResourceNotFound(String msg) {
        super("Could not find " + msg);
    }
}
