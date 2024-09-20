package com.fleetmanagement.api_rest.presentation.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"accessToken", "user"})
public record AuthResponse(
		String accessToken,
		UserDTO user) {
}
