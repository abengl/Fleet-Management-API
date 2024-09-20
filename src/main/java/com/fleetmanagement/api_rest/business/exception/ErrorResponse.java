package com.fleetmanagement.api_rest.business.exception;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorResponse {
	private String error;
	//private Map<String, String> details;

	public ErrorResponse(String error) {
		this.error = error;
	}

	/*public ErrorResponse(String error, Map<String, String> details) {
		this.error = error;
		this.details = details;
	}*/

}
