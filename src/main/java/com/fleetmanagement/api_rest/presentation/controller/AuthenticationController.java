package com.fleetmanagement.api_rest.presentation.controller;

import com.fleetmanagement.api_rest.business.service.UserDetailService;
import com.fleetmanagement.api_rest.presentation.dto.AuthLoginRequest;
import com.fleetmanagement.api_rest.presentation.dto.AuthResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling authentication-related requests.
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private UserDetailService userDetailService;

	/**
	 * Authenticates a user based on the provided login request.
	 *
	 * @param userRequest the login request containing user credentials
	 * @return a response entity containing the authentication response
	 */
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest) {

		AuthResponse response = userDetailService.loginUser(userRequest);
		return ResponseEntity.ok(response);
	}

}
