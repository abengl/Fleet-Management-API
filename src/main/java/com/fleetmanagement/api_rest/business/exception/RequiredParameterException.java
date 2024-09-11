package com.fleetmanagement.api_rest.business.exception;

public class RequiredParameterException extends RuntimeException {
	public RequiredParameterException(String message) {
		super(message);
	}
}
