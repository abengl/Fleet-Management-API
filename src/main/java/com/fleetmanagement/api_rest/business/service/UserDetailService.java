package com.fleetmanagement.api_rest.business.service;

import com.fleetmanagement.api_rest.configuration.security.JwtUtils;
import com.fleetmanagement.api_rest.persistence.entity.UserEntity;
import com.fleetmanagement.api_rest.persistence.repository.UserRepository;
import com.fleetmanagement.api_rest.presentation.dto.AuthLoginRequest;
import com.fleetmanagement.api_rest.presentation.dto.AuthResponse;
import com.fleetmanagement.api_rest.presentation.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for handling user authentication and user details retrieval.
 */
@Service
public class UserDetailService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Authenticates a user and generates a JWT token.
	 *
	 * @param authLoginRequest the login request containing email and password
	 * @return AuthResponse containing the JWT token and user details
	 */
	public AuthResponse loginUser(AuthLoginRequest authLoginRequest) {

		String email = authLoginRequest.email();
		String password = authLoginRequest.password();

		Authentication authentication = this.authenticate(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String accessToken = jwtUtils.createToken(authentication);

		UserEntity userEntity = (UserEntity) authentication.getPrincipal();
		UserDTO userDTO = new UserDTO(userEntity.getId(), userEntity.getEmail());

		AuthResponse authResponse = new AuthResponse(accessToken, userDTO);

		return authResponse;
	}

	/**
	 * Authenticates a user based on email and password.
	 *
	 * @param email    the user's email
	 * @param password the user's password
	 * @return Authentication object if authentication is successful
	 * @throws BadCredentialsException if the password is incorrect
	 */
	public Authentication authenticate(String email, String password) {

		UserDetails userDetails = this.loadUserByUsername(email);

		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Incorrect Password");
		}

		UserEntity userEntity = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

		return new UsernamePasswordAuthenticationToken(userEntity, null, userDetails.getAuthorities());
	}

	/**
	 * Loads a user by their email.
	 *
	 * @param email the user's email
	 * @return UserDetails object containing user information
	 * @throws UsernameNotFoundException if the user is not found
	 */
	@Override
	public UserDetails loadUserByUsername(String email) {

		UserEntity userEntity = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

		List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

		authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(userEntity.getRole().getRoleEnum().name())));

		return new User(userEntity.getEmail(), userEntity.getPassword(), userEntity.isEnabled(),
				userEntity.isAccountNonExpired(), userEntity.isCredentialsNonExpired(),
				userEntity.isAccountNonLocked(),
				authorityList);
	}

}
