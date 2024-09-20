package com.fleetmanagement.api_rest.presentation.dto;

import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public record AuthCreateRoleRequest(
		@Size(max = 3, message = "AuthCreateRoleRequest - The user cannot have more than 3 roles") List<String> roleListName) {
}