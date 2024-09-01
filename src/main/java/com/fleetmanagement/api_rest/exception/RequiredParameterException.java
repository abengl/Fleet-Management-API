package com.fleetmanagement.api_rest.exception;

public class RequiredParameterException extends RuntimeException {

	public RequiredParameterException(String message) {
		super(message);
	}
}
