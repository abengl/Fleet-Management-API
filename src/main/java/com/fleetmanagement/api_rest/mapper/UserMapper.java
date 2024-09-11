package com.fleetmanagement.api_rest.mapper;

import com.fleetmanagement.api_rest.dto.UserCreateDTO;
import com.fleetmanagement.api_rest.dto.UserResponseDTO;
import com.fleetmanagement.api_rest.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

	// Map from User entity to UserResponseDTO for output
	UserResponseDTO toUserResponseDTO(User user);

	// Map from UserCreateDTO to User entity for input
	User toUser(UserCreateDTO userCreateDTO);
}
