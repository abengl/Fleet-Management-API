package com.fleetmanagement.api_rest.presentation.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * A record representing an authentication login request.
 * This record contains the email and password fields, both of which are mandatory.
 */
public record AuthLoginRequest(@NotBlank(message = "AuthLoginRequest - Email must not be blank.") String email,
							   @NotBlank(message = "AuthLoginRequest - Password must not be blank.") String password) {
}