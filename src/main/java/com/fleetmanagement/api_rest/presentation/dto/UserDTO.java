package com.fleetmanagement.api_rest.presentation.dto;

/**
 * Data Transfer Object (DTO) representing a user.
 * This class contains the user's ID and email.
 */
public record UserDTO(Integer id, String email) {
}
