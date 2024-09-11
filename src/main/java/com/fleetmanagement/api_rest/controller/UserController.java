package com.fleetmanagement.api_rest.controller;

import com.fleetmanagement.api_rest.dto.TaxiDTO;
import com.fleetmanagement.api_rest.dto.UserCreateDTO;
import com.fleetmanagement.api_rest.dto.UserResponseDTO;
import com.fleetmanagement.api_rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<List<UserResponseDTO>> getUsers(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "limit", defaultValue = "10") int limit) {
		List<UserResponseDTO> users = userService.getAllUsers(page, limit);
		return ResponseEntity.ok(users);
	}

	@PostMapping
	public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO userCreateDTO) {
		UserResponseDTO createdUser = userService.createUser(userCreateDTO);

		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<UserResponseDTO> deleteUser(@PathVariable("id") Integer id) {
		UserResponseDTO deletedUser = userService.deleteUser(id);

		return ResponseEntity.ok(deletedUser);
	}
}
