package com.fleetmanagement.api_rest.utils.mapper;

import com.fleetmanagement.api_rest.persistence.entity.UserEntity;
import com.fleetmanagement.api_rest.presentation.dto.UserCreateDTO;
import com.fleetmanagement.api_rest.presentation.dto.UserResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

	// Map from User entity to UserResponseDTO for output
	UserResponseDTO toUserResponseDTO(UserEntity userEntity);

	// Map from UserCreateDTO to User entity for input
	UserEntity toUser(UserCreateDTO userCreateDTO);
}
