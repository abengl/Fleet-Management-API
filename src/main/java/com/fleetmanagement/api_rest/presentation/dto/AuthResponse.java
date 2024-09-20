package com.fleetmanagement.api_rest.presentation.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

// 1st parameter String username
@JsonPropertyOrder({"accessToken", "user"})
public record AuthResponse(
		String accessToken,
		UserDTO user) {
}

//@JsonPropertyOrder({"email", "message", "status", "jwt"})
//public record AuthResponse(
//		String email,
//		String message,
//		String jwt,
//		Boolean status) {
//}