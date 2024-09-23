package com.fleetmanagement.api_rest.presentation.dto;

import com.fleetmanagement.api_rest.persistence.entity.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for creating a new user.
 * This class contains information about the user's name, email, password, and role.
 * It also includes flags for account status.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {
	private String name;
	private String email;
	private String password;
	private final boolean isEnabled = true;
	private final boolean accountNonExpired = true;
	private final boolean accountNonLocked = true;
	private final boolean credentialsNonExpired = true;
	private RoleEnum role;
}
