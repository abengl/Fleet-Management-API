package com.fleetmanagement.api_rest.exception;

public class InvalidLimitException extends RuntimeException {
	public InvalidLimitException(String message) {
		super(message);
	}
}
