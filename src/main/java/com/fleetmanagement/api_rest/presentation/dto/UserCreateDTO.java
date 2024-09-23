package com.fleetmanagement.api_rest.presentation.dto;

import com.fleetmanagement.api_rest.persistence.entity.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
