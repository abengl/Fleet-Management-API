package com.fleetmanagement.api_rest.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest(@NotBlank(message = "Email must not be blank.") String email,
							   @NotBlank(message = "Password must not be blank.") String password) {
}
