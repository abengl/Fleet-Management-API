package com.fleetmanagement.api_rest.service;

import com.fleetmanagement.api_rest.dto.TaxiDTO;
import com.fleetmanagement.api_rest.dto.UserCreateDTO;
import com.fleetmanagement.api_rest.dto.UserResponseDTO;
import com.fleetmanagement.api_rest.exception.InvalidLimitException;
import com.fleetmanagement.api_rest.exception.RequiredParameterException;
import com.fleetmanagement.api_rest.exception.UserAlreadyExistsException;
import com.fleetmanagement.api_rest.mapper.UserMapper;
import com.fleetmanagement.api_rest.model.Taxi;
import com.fleetmanagement.api_rest.model.User;
import com.fleetmanagement.api_rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

	public UserResponseDTO createUser(UserCreateDTO userCreateDTO) {

		if(userCreateDTO.getPassword() == null || userCreateDTO.getPassword().isEmpty()) {
			throw new RequiredParameterException("Password value is missing.");
		}

		if(userCreateDTO.getEmail() == null || userCreateDTO.getEmail().isEmpty()) {
			throw new RequiredParameterException("Email value is missing.");
		}

		if(userRepository.existsByEmail(userCreateDTO.getEmail())) {
			throw new UserAlreadyExistsException(userCreateDTO.getEmail());
		}

		// Map DTO to entity
		User user = userMapper.toUser(userCreateDTO);
		User savedUser = userRepository.save(user);

		// Map back to ResponseDTO and return the saved user
		return userMapper.toUserResponseDTO(savedUser);

	}

	public List<UserResponseDTO> getAllUsers(int page, int limit) {

		if (page < 0) {
			throw new InvalidLimitException("Page number cannot be negative");
		}
		if (limit <= 0) {
			throw new InvalidLimitException("Limit must be greater than zero");
		}

		Pageable pageable = PageRequest.of(page, limit);
		Page<User> usersPage = userRepository.findAll(pageable);

		return usersPage.stream().map(userMapper::toUserResponseDTO).collect(Collectors.toList());
	}
}
