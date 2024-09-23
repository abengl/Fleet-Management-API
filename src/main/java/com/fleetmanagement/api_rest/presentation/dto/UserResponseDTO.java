package com.fleetmanagement.api_rest.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for user responses.
 * This class contains information about the user's ID, name, and email.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
	private Integer id;
	private String name;
	private String email;
}
