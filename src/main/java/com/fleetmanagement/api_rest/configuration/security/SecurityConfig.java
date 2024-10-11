package com.fleetmanagement.api_rest.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Configures the security aspects of the application, including the security filter chain,
 * authentication manager, authentication provider, and password encoder.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtUtils jwtUtils;

	/**
	 * Configures the security filter chain for the application.
	 *
	 * @param httpSecurity the {@link HttpSecurity} to modify
	 * @return the configured {@link SecurityFilterChain}
	 * @throws Exception if an error occurs while configuring the security filter chain
	 */
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

		return httpSecurity.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(http -> {
					http.requestMatchers(HttpMethod.GET, "/actuator/health").permitAll();
					http.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();
					http.requestMatchers(HttpMethod.GET, "/taxis/**").hasAnyRole("ADMIN", "DEVELOPER", "USER",
							"GUEST");
					http.requestMatchers(HttpMethod.GET, "/trajectories").hasAnyRole("ADMIN", "DEVELOPER", "USER");
					http.requestMatchers(HttpMethod.GET, "/trajectories/latest")
							.hasAnyRole("ADMIN", "DEVELOPER", "USER");
					http.requestMatchers(HttpMethod.GET, "/users").hasAnyRole("ADMIN", "DEVELOPER");
					http.requestMatchers(HttpMethod.POST, "/users").hasAnyRole("ADMIN", "DEVELOPER");
					http.requestMatchers(HttpMethod.PATCH, "/users/**").hasAnyRole("ADMIN", "DEVELOPER");
					http.requestMatchers(HttpMethod.DELETE, "/users/**").hasAnyRole("ADMIN", "DEVELOPER");

					http.anyRequest().authenticated();
				}).addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class).build();
	}

	/**
	 * Defines a Spring Bean for the {@link AuthenticationManager}.
	 *
	 * @param authenticationConfiguration the {@link AuthenticationConfiguration} to use
	 * @return the {@link AuthenticationManager} obtained from the {@link AuthenticationConfiguration}
	 * @throws Exception if an error occurs while obtaining the {@link AuthenticationManager}
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {

		return authenticationConfiguration.getAuthenticationManager();
	}

	/**
	 * Defines a Spring Bean for the {@link PasswordEncoder}.
	 * This bean is used to encode and verify passwords using the BCrypt hashing algorithm.
	 *
	 * @return the configured {@link PasswordEncoder}
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

}
