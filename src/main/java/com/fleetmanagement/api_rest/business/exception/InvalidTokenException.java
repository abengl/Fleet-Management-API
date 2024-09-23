package com.fleetmanagement.api_rest.business.exception;

/**
 * Exception thrown when an invalid token is encountered.
 */
public class InvalidTokenException extends RuntimeException {

    /**
     * Constructs a new InvalidTokenException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidTokenException(String message) {
        super(message);
    }
}

