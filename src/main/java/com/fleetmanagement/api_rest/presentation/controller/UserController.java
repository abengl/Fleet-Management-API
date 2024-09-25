package com.fleetmanagement.api_rest.presentation.controller;

import com.fleetmanagement.api_rest.business.service.UserService;
import com.fleetmanagement.api_rest.presentation.dto.UserCreateDTO;
import com.fleetmanagement.api_rest.presentation.dto.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling user-related requests.
 */
@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Retrieves a list of users based on the provided page and limit.
	 *
	 * @param page  the page number for pagination (default is 0)
	 * @param limit the maximum number of results per page (default is 10)
	 * @return a ResponseEntity containing a list of UserResponseDTO objects
	 */
	@GetMapping
	public ResponseEntity<List<UserResponseDTO>> getUsers(@RequestParam(name = "page", defaultValue = "0") int page,
														  @RequestParam(name = "limit", defaultValue = "10") int limit) {
		List<UserResponseDTO> users = userService.getAllUsers(page, limit);
		return ResponseEntity.ok(users);
	}

	/**
	 * Creates a new user with the provided UserCreateDTO.
	 *
	 * @param userCreateDTO the data transfer object containing user creation details
	 * @return a ResponseEntity containing the created UserResponseDTO
	 */
	@PostMapping
	public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO userCreateDTO) {
		UserResponseDTO createdUser = userService.createUser(userCreateDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	/**
	 * Updates the name of an existing user with the provided UserCreateDTO and user ID.
	 *
	 * @param userCreateDTO the data transfer object containing user update details
	 * @param id            the ID of the user to be updated
	 * @return a ResponseEntity containing the updated UserResponseDTO
	 */
	@PatchMapping("/{id}")
	public ResponseEntity<UserResponseDTO> updateUserName(@RequestBody UserCreateDTO userCreateDTO,
														  @PathVariable("id") Integer id) {
		UserResponseDTO updatedUser = userService.updateUserByName(userCreateDTO, id);
		return ResponseEntity.ok(updatedUser);
	}

	/**
	 * Deletes an existing user with the provided user ID.
	 *
	 * @param id the ID of the user to be deleted
	 * @return a ResponseEntity containing the deleted UserResponseDTO
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<UserResponseDTO> deleteUser(@PathVariable("id") Integer id) {
		UserResponseDTO deletedUser = userService.deleteUser(id);
		return ResponseEntity.ok(deletedUser);
	}
}
