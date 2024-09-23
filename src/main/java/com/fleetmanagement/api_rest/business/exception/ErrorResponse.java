package com.fleetmanagement.api_rest.business.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents an error response with a single error message.
 */
@Getter
@Setter
public class ErrorResponse {
	private String error;

	/**
	 * Constructs an ErrorResponse with the specified error message.
	 *
	 * @param error the error message
	 */
	public ErrorResponse(String error) {
		this.error = error;
	}
}
