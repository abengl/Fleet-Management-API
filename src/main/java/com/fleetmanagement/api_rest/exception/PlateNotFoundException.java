package com.fleetmanagement.api_rest.exception;

public class PlateNotFoundException extends RuntimeException {
	public PlateNotFoundException(String message) {
		super(message);
	}
}
