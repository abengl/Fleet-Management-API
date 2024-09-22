package com.fleetmanagement.api_rest.presentation.dto;

import com.fleetmanagement.api_rest.persistence.entity.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public record AuthCreateRoleRequest(
		@NotBlank(message = "AuthCreateRoleRequest - Role name must not be blank") RoleEnum roleName) {
}
