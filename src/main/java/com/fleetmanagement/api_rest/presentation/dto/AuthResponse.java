package com.fleetmanagement.api_rest.presentation.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * A record representing an authentication response.
 * This record contains the access token and user details.
 *
 * @param accessToken the access token for authentication
 * @param user        the user details associated with the authentication
 */
@JsonPropertyOrder({"accessToken", "user"})
public record AuthResponse(String accessToken, UserDTO user) {
}
