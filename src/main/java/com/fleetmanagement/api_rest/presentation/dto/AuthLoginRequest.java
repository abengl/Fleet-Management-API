package com.fleetmanagement.api_rest.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest(@NotBlank(message = "AuthLoginRequest - Email must not be blank.") String email,
							   @NotBlank(message = "AuthLoginRequest - Password must not be blank.") String password) {
}
