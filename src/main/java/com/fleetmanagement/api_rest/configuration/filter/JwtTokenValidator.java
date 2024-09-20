package com.fleetmanagement.api_rest.configuration.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fleetmanagement.api_rest.business.exception.InvalidTokenException;
import com.fleetmanagement.api_rest.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Filter that validates JWT tokens for incoming HTTP requests.
 * This filter checks the Authorization header for a JWT token, validates it,
 * extracts user details and authorities, and sets the authentication information
 * in the SecurityContext.
 */
public class JwtTokenValidator extends OncePerRequestFilter {

	private final List<String> excludedPaths = List.of("/auth/login", "/auth/**");
			// public paths to exclude from token validation
	private JwtUtils jwtUtils; // utility class for JWT operations

	public JwtTokenValidator(JwtUtils jwtUtils) {
		this.jwtUtils = jwtUtils;
	}

	/**
	 * Determines whether the request should not be filtered.
	 * This method checks if the request URI matches any of the excluded paths.
	 *
	 * @param request the {@link HttpServletRequest} to check
	 * @return {@code true} if the request should not be filtered, {@code false} otherwise
	 */
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		System.out.println("shouldNotFilter -> " + request);
		String path = request.getRequestURI(); // retrieves the uri
		return excludedPaths.stream().anyMatch(path::startsWith); // checks if the uri is in the excluded paths
	}

	/**
	 * Filters incoming HTTP requests to validate JWT tokens.
	 * This method retrieves the JWT token from the Authorization header, validates it,
	 * extracts user details and authorities, and sets the authentication information
	 * in the SecurityContext.
	 *
	 * @param request     the HTTP request
	 * @param response    the HTTP response
	 * @param filterChain the filter chain
	 * @throws ServletException if an error occurs during the filtering process
	 * @throws IOException      if an I/O error occurs during the filtering process
	 */
	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request,
			@NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {

		System.out.println("JwtTokenValidator -> doFilterInternal -> ");

		// Retrieve the Authorization header from the HTTP request
		String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

		// Check if the token is missing or does not start with "Bearer "
		if (jwtToken == null || !jwtToken.startsWith("Bearer ")) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("error", "No token provided.");
			response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
			response.getWriter().flush();
			return;
		}

		try {
			// Remove the "Bearer " prefix from the token
			jwtToken = jwtToken.substring(7);

			// Validate the token and decode it
			DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);
			System.out.println("JwtTokenValidator -> doFilterInternal -> decoded jwt -> " + decodedJWT);

			// Extract the username from the decoded token
			String username = jwtUtils.extractUsername(decodedJWT);
			System.out.println("JwtTokenValidator -> doFilterInternal -> username -> " + username);

			// Extract the authorities (roles/permissions) from the decoded token
			String stringAuthorities = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();

			// Convert the authorities from a comma-separated string to a collection of GrantedAuthority objects
			Collection<? extends GrantedAuthority> authorities =
					AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities);
			System.out.println("JwtTokenValidator -> doFilterInternal -> authorities -> " + authorities);

			// Create a new SecurityContext and set the authentication information
			SecurityContext context = SecurityContextHolder.createEmptyContext();
			Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
			context.setAuthentication(authenticationToken);
			SecurityContextHolder.setContext(context);

			// Continue the filter chain if the token is valid
			filterChain.doFilter(request, response);
		} catch (InvalidTokenException e) {
			System.out.println("JwtTokenValidator -> doFilterInternal -> InvalidTokenException");

			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("error", e.getMessage());
			response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
			response.getWriter().flush();
			return;
		}
	}
}

