package com.fleetmanagement.api_rest.presentation.mapper;

import com.fleetmanagement.api_rest.presentation.dto.UserCreateDTO;
import com.fleetmanagement.api_rest.presentation.dto.UserResponseDTO;
import com.fleetmanagement.api_rest.persistence.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

	// Map from User entity to UserResponseDTO for output
	UserResponseDTO toUserResponseDTO(User user);

	// Map from UserCreateDTO to User entity for input
	User toUser(UserCreateDTO userCreateDTO);
}
