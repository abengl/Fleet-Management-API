package com.fleetmanagement.api_rest.exception;

public class InvalidPageException extends RuntimeException {
	public InvalidPageException(String message) {
		super(message);
	}
}
