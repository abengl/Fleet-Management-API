package com.fleetmanagement.api_rest.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Utility class for handling JSON Web Tokens (JWT) in the application.
 * This class provides methods for creating, validating, and extracting information from JWT tokens.
 * It uses the HMAC256 algorithm for signing the tokens and includes user details and authorities in the token claims.
 *
 * <p>Configuration properties used by this class:
 * <ul>
 *   <li><code>security.jwt.key.private</code>: The private key used for signing the JWT tokens.</li>
 *   <li><code>security.jwt.user.generator</code>: The user or entity that issues the JWT tokens.</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>
 * {@code
 * JwtUtils jwtUtils = new JwtUtils();
 * String token = jwtUtils.createToken(authentication);
 * DecodedJWT decodedJWT = jwtUtils.validateToken(token);
 * String username = jwtUtils.extractUsername(decodedJWT);
 * Claim claim = jwtUtils.getSpecificClaim(decodedJWT, "claimName");
 * }
 * </pre>
 *
 * <p>This class is managed by the Spring IoC container as a Spring bean.
 *
 * @see com.auth0.jwt.JWT
 * @see com.auth0.jwt.algorithms.Algorithm
 * @see com.auth0.jwt.interfaces.DecodedJWT
 * @see org.springframework.security.core.Authentication
 * @see org.springframework.security.core.GrantedAuthority
 */

@Component // define a Spring bean that will be managed by the Spring IoC container
public class JwtUtils {
	// injects the sha256 generated key to sign the JWT token
	@Value("${security.jwt.key.private}")
	private String privateKey;
	// injects the user that issues the key
	@Value("${security.jwt.user.generator}")
	private String userGenerator;

	/**
	 * Generates a JWT token for the authenticated user.
	 * This token is used for securing API endpoints by ensuring that the user is authenticated and authorized.
	 * The token includes user details and authorities, and it is signed using a private key.
	 *
	 * @param authentication the authentication object containing user details
	 * @return a signed JWT token as a String
	 */
	public String createToken(Authentication authentication) {

		Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

		String username = authentication.getPrincipal().toString();

		String authorities = authentication.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		String jwtToken = JWT.create()
				.withIssuer(this.userGenerator)
				.withSubject(username)
				.withClaim("authorities", authorities)
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + 1800000))
				.withJWTId(UUID.randomUUID().toString())
				.withNotBefore(new Date(System.currentTimeMillis()))
				.sign(algorithm);

		return jwtToken;
	}

	/**
	 * Validates a JWT token.
	 * This method verifies the provided JWT token using the configured private key and issuer.
	 * If the token is valid, it returns the decoded JWT object.
	 * If the token is invalid, it throws a RuntimeException.
	 *
	 * @param token the JWT token to be validated
	 * @return the decoded JWT object if the token is valid
	 * @throws RuntimeException if the token is invalid
	 */
	public DecodedJWT validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

			DecodedJWT decodedJWT = JWT.require(algorithm)
					.withIssuer(this.userGenerator)
					.build()
					.verify(token);

			return decodedJWT;

		} catch (Exception e) {
			throw new RuntimeException("Token invalid, not Authorized");
		}
	}

	/**
	 * Extracts the username from the decoded JWT token.
	 *
	 * @param decodedJWT the decoded JWT token
	 * @return the username extracted from the token
	 */
	public String extractUsername(DecodedJWT decodedJWT) {
		return decodedJWT.getSubject().toString();
	}

	/**
	 * Retrieves a specific claim from the decoded JWT token.
	 *
	 * @param decodedJWT the decoded JWT token
	 * @param claimName  the name of the claim to retrieve
	 * @return the claim associated with the specified name
	 */
	public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
		return decodedJWT.getClaim(claimName);
	}

}
