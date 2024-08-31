package com.fleetmanagement.api_rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InvalidPageException.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleInvalidPageException(InvalidPageException ex) {
		ErrorResponse errorResponse = new ErrorResponse("Invalid page: " + ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidLimitException.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleInvalidLimitException(InvalidLimitException ex) {
		ErrorResponse errorResponse = new ErrorResponse("Invalid limit: " + ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(PlateNotFoundException.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handlePlateNotFoundException(PlateNotFoundException ex) {
		ErrorResponse errorResponse = new ErrorResponse("Plate not found: " + ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
		ErrorResponse errorResponse = new ErrorResponse("An error occurred: " + ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
