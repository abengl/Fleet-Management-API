package com.fleetmanagement.api_rest.presentation.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

// 1st parameter @NotBlank String username
public record AuthCreateUserRequest(
		@NotBlank(message = "AuthCreateUserRequest - missing parameter: name") String name,
		@NotBlank(message = "AuthCreateUserRequest - missing parameter: email") String email,
		@NotBlank(message = "AuthCreateUserRequest - missing parameter: password") String password,
		@Valid AuthCreateRoleRequest roleRequest) {
}
