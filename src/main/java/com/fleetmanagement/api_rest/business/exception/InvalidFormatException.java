package com.fleetmanagement.api_rest.business.exception;

/**
 * Exception thrown when an invalid format is encountered.
 */
public class InvalidFormatException extends RuntimeException {

    /**
     * Constructs a new InvalidFormatException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidFormatException(String message) {
        super(message);
    }
}
