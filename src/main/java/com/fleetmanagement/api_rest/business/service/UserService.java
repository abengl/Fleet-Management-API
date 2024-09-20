package com.fleetmanagement.api_rest.business.service;

import com.fleetmanagement.api_rest.business.exception.UserAlreadyExistsException;
import com.fleetmanagement.api_rest.business.exception.ValueNotFoundException;
import com.fleetmanagement.api_rest.persistence.entity.UserEntity;
import com.fleetmanagement.api_rest.persistence.repository.UserRepository;
import com.fleetmanagement.api_rest.presentation.dto.UserCreateDTO;
import com.fleetmanagement.api_rest.presentation.dto.UserResponseDTO;
import com.fleetmanagement.api_rest.utils.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	@Autowired
	public UserService(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	public List<UserResponseDTO> getAllUsers(int page, int limit) {

		if (page < 0) {
			throw new InvalidParameterException("Page number cannot be negative");
		}
		if (limit <= 0) {
			throw new InvalidParameterException("Limit must be greater than zero");
		}

		Pageable pageable = PageRequest.of(page, limit);
		Page<UserEntity> usersPage = userRepository.findAll(pageable);

		return usersPage.stream().map(userMapper::toUserResponseDTO).collect(Collectors.toList());
	}

	public UserResponseDTO createUser(UserCreateDTO userCreateDTO) {

		if(userCreateDTO.getPassword() == null || userCreateDTO.getPassword().isEmpty()) {
			throw new InvalidParameterException("Password value is missing.");
		}

		if(userCreateDTO.getEmail() == null || userCreateDTO.getEmail().isEmpty()) {
			throw new InvalidParameterException("Email value is missing.");
		}

		if(userRepository.existsUserByEmail(userCreateDTO.getEmail())) {
			throw new UserAlreadyExistsException(userCreateDTO.getEmail());
		}

		// Map DTO to entity
		UserEntity userEntity = userMapper.toUser(userCreateDTO);
		UserEntity savedUserEntity = userRepository.save(userEntity);

		// Map back to ResponseDTO and return the saved user
		return userMapper.toUserResponseDTO(savedUserEntity);

	}

	public UserResponseDTO updateUserByName(UserCreateDTO userCreateDTO, Integer id) {

		if(userCreateDTO.getPassword() != null) {
			throw new InvalidParameterException("Password field can't be modified.");
		}

		if(userCreateDTO.getEmail() != null) {
			throw new InvalidParameterException("Email field can't be modified.");
		}

		if (userCreateDTO.getName() == null) {
			throw new InvalidParameterException("Request body is empty.");
		}

		UserEntity userEntity = userRepository.findById(id)
				.orElseThrow(() -> new ValueNotFoundException("User with id " + id + " doesn't exist."));

		userEntity.setName(userCreateDTO.getName());

		return userMapper.toUserResponseDTO(userEntity);
	}

	public UserResponseDTO deleteUser(Integer id) {
		UserEntity userEntity = userRepository.findById(id)
				.orElseThrow(() -> new ValueNotFoundException("User with id " + id + " doesn't exist."));

		userRepository.delete(userEntity);

		// Map back to ResponseDTO and return the saved user
		return userMapper.toUserResponseDTO(userEntity);
	}
}
