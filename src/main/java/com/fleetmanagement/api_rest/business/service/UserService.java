package com.fleetmanagement.api_rest.business.service;

import com.fleetmanagement.api_rest.business.exception.UserAlreadyExistsException;
import com.fleetmanagement.api_rest.business.exception.ValueNotFoundException;
import com.fleetmanagement.api_rest.persistence.entity.RoleEntity;
import com.fleetmanagement.api_rest.persistence.entity.UserEntity;
import com.fleetmanagement.api_rest.persistence.repository.RoleRepository;
import com.fleetmanagement.api_rest.persistence.repository.UserRepository;
import com.fleetmanagement.api_rest.presentation.dto.UserCreateDTO;
import com.fleetmanagement.api_rest.presentation.dto.UserResponseDTO;
import com.fleetmanagement.api_rest.utils.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing user-related operations such as creating, updating, retrieving, and deleting users.
 */
@Service
public class UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper,
					   PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * Retrieves a paginated list of all users.
	 *
	 * @param page  the page number to retrieve
	 * @param limit the number of items per page
	 * @return a list of UserResponseDTOs
	 * @throws InvalidParameterException if page or limit parameters are invalid
	 */
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


	/**
	 * Creates a new user with the provided details.
	 *
	 * @param userCreateDTO the data transfer object containing user details
	 * @return the created UserResponseDTO
	 * @throws InvalidParameterException  if required fields are missing or invalid
	 * @throws UserAlreadyExistsException if a user with the given email already exists
	 * @throws ValueNotFoundException     if the specified role does not exist
	 */
	public UserResponseDTO createUser(UserCreateDTO userCreateDTO) {

		if (userCreateDTO.getPassword() == null || userCreateDTO.getPassword().isEmpty()) {
			throw new InvalidParameterException("Password value is missing.");
		}

		if (userCreateDTO.getEmail() == null || userCreateDTO.getEmail().isEmpty()) {
			throw new InvalidParameterException("Email value is missing.");
		}

		if (userRepository.existsUserByEmail(userCreateDTO.getEmail())) {
			throw new UserAlreadyExistsException(userCreateDTO.getEmail());
		}

		String encodedPassword = passwordEncoder.encode(userCreateDTO.getPassword());
		userCreateDTO.setPassword(encodedPassword);

		RoleEntity role = roleRepository.findByRoleEnum(userCreateDTO.getRole())
				.orElseThrow(() -> new ValueNotFoundException("Role " + userCreateDTO.getRole() + " doesn't exist."));

		UserEntity userEntity = userMapper.toUser(userCreateDTO);
		userEntity.setRole(role);
		UserEntity savedUserEntity = userRepository.save(userEntity);

		return userMapper.toUserResponseDTO(savedUserEntity);

	}

	/**
	 * Updates the name of an existing user by their ID.
	 *
	 * @param userCreateDTO the data transfer object containing the new name
	 * @param id            the ID of the user to update
	 * @return the updated UserResponseDTO
	 * @throws InvalidParameterException if the request contains invalid fields
	 * @throws ValueNotFoundException    if the user with the given ID does not exist
	 */
	public UserResponseDTO updateUserByName(UserCreateDTO userCreateDTO, Integer id) {

		if (userCreateDTO.getPassword() != null) {
			throw new InvalidParameterException("Password field can't be modified.");
		}

		if (userCreateDTO.getEmail() != null) {
			throw new InvalidParameterException("Email field can't be modified.");
		}

		if (userCreateDTO.getName() == null) {
			throw new InvalidParameterException("Request body is empty.");
		}

		UserEntity userEntity = userRepository.findById(id)
				.orElseThrow(() -> new ValueNotFoundException("User with id " + id + " doesn't exist."));

		userEntity.setName(userCreateDTO.getName());
		userRepository.save(userEntity);
		return userMapper.toUserResponseDTO(userEntity);
	}

	/**
	 * Deletes a user by their ID.
	 *
	 * @param id the ID of the user to delete
	 * @return the deleted UserResponseDTO
	 * @throws ValueNotFoundException if the user with the given ID does not exist
	 */
	public UserResponseDTO deleteUser(Integer id) {
		UserEntity userEntity = userRepository.findById(id)
				.orElseThrow(() -> new ValueNotFoundException("User with id " + id + " doesn't exist."));

		userRepository.delete(userEntity);

		return userMapper.toUserResponseDTO(userEntity);
	}
}
