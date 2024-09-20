package com.fleetmanagement.api_rest.presentation.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

// 1st parameter @NotBlank String username
public record AuthCreateUserRequest(
		@NotBlank String name,
		@NotBlank String email,
		@NotBlank String password,
		@Valid AuthCreateRoleRequest roleRequest) {
}
