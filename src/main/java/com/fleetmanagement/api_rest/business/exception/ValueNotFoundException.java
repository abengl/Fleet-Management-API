package com.fleetmanagement.api_rest.business.exception;

public class ValueNotFoundException extends RuntimeException {
	public ValueNotFoundException(String message) {
		super(message);
	}
}
