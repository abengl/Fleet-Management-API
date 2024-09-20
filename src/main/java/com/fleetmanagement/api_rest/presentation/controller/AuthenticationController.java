package com.fleetmanagement.api_rest.presentation.controller;

import com.fleetmanagement.api_rest.business.service.UserDetailService;
import com.fleetmanagement.api_rest.presentation.dto.AuthCreateUserRequest;
import com.fleetmanagement.api_rest.presentation.dto.AuthLoginRequest;
import com.fleetmanagement.api_rest.presentation.dto.AuthResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private UserDetailService userDetailService;

	@PostMapping("/sign-up")
	public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUserRequest userRequest) {
		return new ResponseEntity<>(this.userDetailService.createUser(userRequest), HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest) {
		System.out.println("AuthenticationController -> login -> userRequest " + userRequest);
		return new ResponseEntity<>(this.userDetailService.loginUser(userRequest), HttpStatus.OK);
	}
}
