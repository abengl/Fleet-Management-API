package com.fleetmanagement.api_rest.configuration;

import com.fleetmanagement.api_rest.business.service.UserDetailService;
import com.fleetmanagement.api_rest.configuration.filter.JwtTokenValidator;
import com.fleetmanagement.api_rest.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Configuration class for setting up the security aspects of the application.
 * This class configures the security filter chain, authentication manager,
 * authentication provider, and password encoder.
 */
@Configuration // indicates that the class can be used by the Spring IoC container as a source of bean definitions
@EnableWebSecurity // Spring Security framework, enables and configures web security
public class SecurityConfig {

	@Autowired
	private JwtUtils jwtUtils;

	/**
	 * Configures the security filter chain for the application.
	 *
	 * @param httpSecurity           the {@link HttpSecurity} to modify
	 * @param authenticationProvider the {@link AuthenticationProvider} to use
	 * @return the configured {@link SecurityFilterChain}
	 * @throws Exception if an error occurs while configuring the security filter chain
	 */
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationProvider authenticationProvider)
			throws Exception {

		System.out.println("SecurityConfig -> SecurityFilterChain -> httpSecurity ");
		return httpSecurity
				// Disable Cross-Site Request Forgery (CSRF) protection
				.csrf(csrf -> csrf.disable())
				//stateless session the server will not store any session information
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				// Authorization rules
				.authorizeHttpRequests(http -> {
					// Public EndPoints
					http.requestMatchers(HttpMethod.POST, "/auth/login").permitAll();

					// Private EndPoints
					http.requestMatchers(HttpMethod.GET, "/taxis").hasAuthority("READ");
					http.requestMatchers(HttpMethod.GET, "/trajectories/**").hasAuthority("READ");
					http.requestMatchers(HttpMethod.GET, "/users").hasAuthority("READ");
					http.requestMatchers(HttpMethod.POST, "/users").hasAuthority("CREATE");
					http.requestMatchers(HttpMethod.DELETE, "/users").hasAuthority("DELETE");
					http.requestMatchers(HttpMethod.PATCH, "/users").hasAuthority("UPDATE");

					http.anyRequest().denyAll();
				})
				// Custom JWT filter to validate the token before the BasicAuthenticationFilter
				.addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
				// Builds the security configuration
				.build();
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

		System.out.println("SecurityConfig -> AuthenticationManager -> authenticationConfiguration");
		return authenticationConfiguration.getAuthenticationManager();
	}

	/**
	 * Defines a Spring Bean for the {@link AuthenticationProvider} using a {@link DaoAuthenticationProvider}.
	 * This bean is used to retrieve user details and validate passwords during the authentication process.
	 *
	 * @param userDetailService the {@link UserDetailService} to use for loading user-specific data
	 * @return the configured {@link AuthenticationProvider}
	 */
	@Bean
	public AuthenticationProvider authenticationProvider(UserDetailService userDetailService) {

		System.out.println("SecurityConfig -> AuthenticationProvider -> userDetailService");
		DaoAuthenticationProvider provider =
				new DaoAuthenticationProvider(); // Retrieves user details from a UserDetailsService
		provider.setPasswordEncoder(passwordEncoder()); // Encodes and verifies the password
		provider.setUserDetailsService(userDetailService); // Sets user details
		return provider;
	}

	/**
	 * Defines a Spring Bean for the {@link PasswordEncoder}.
	 * This bean is used to encode and verify passwords using the BCrypt hashing algorithm.
	 *
	 * @return the configured {@link PasswordEncoder}
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {

		System.out.println("SecurityConfig -> PasswordEncoder");
		return new BCryptPasswordEncoder();
	}
}
