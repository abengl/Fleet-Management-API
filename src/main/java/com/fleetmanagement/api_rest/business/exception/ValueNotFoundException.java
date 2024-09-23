package com.fleetmanagement.api_rest.business.exception;

/**
 * Exception thrown when a specified value is not found.
 */
public class ValueNotFoundException extends RuntimeException {

    /**
     * Constructs a new ValueNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public ValueNotFoundException(String message) {
        super(message);
    }
}