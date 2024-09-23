package com.fleetmanagement.api_rest.utils.mapper;

import com.fleetmanagement.api_rest.persistence.entity.UserEntity;
import com.fleetmanagement.api_rest.presentation.dto.UserCreateDTO;
import com.fleetmanagement.api_rest.presentation.dto.UserResponseDTO;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between UserEntity and User DTOs.
 * Utilizes MapStruct for automatic mapping.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

	/**
	 * Converts a UserEntity to a UserResponseDTO.
	 *
	 * @param userEntity the UserEntity to convert
	 * @return the converted UserResponseDTO
	 */
	UserResponseDTO toUserResponseDTO(UserEntity userEntity);

	/**
	 * Converts a UserCreateDTO to a UserEntity.
	 *
	 * @param userCreateDTO the UserCreateDTO to convert
	 * @return the converted UserEntity
	 */
	UserEntity toUser(UserCreateDTO userCreateDTO);
}
