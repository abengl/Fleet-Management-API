package com.fleetmanagement.api_rest.configuration.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fleetmanagement.api_rest.business.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Utility class for handling JSON Web Tokens (JWT).
 * Provides methods for creating, validating, and extracting information from JWT tokens.
 */
@Component
public class JwtUtils {

	@Value("${jwt.key.private}")
	private String privateKey;
	@Value("${jwt.user.generator}")
	private String userGenerator;
	@Value("${jwt.expiration.time}")
	private String timeExpiration;

	/**
	 * Generates a JWT token for the authenticated user.
	 *
	 * @param authentication the authentication object containing user details
	 * @return a signed JWT token as a String
	 */
	public String createToken(Authentication authentication) {

		Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

		String username = authentication.getPrincipal().toString();

		String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		String jwtToken =
				JWT.create().withIssuer(this.userGenerator).withSubject(username).withClaim("authorities", authorities)
						.withIssuedAt(new Date(System.currentTimeMillis()))
						.withExpiresAt(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
						.withJWTId(UUID.randomUUID().toString()).withNotBefore(new Date(System.currentTimeMillis()))
						.sign(algorithm);
		return jwtToken;
	}

	/**
	 * Validates a JWT token.
	 *
	 * @param token the JWT token to be validated
	 * @return the decoded JWT object if the token is valid
	 * @throws InvalidTokenException if the token is invalid
	 */
	public DecodedJWT validateToken(String token) {

		try {

			Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

			DecodedJWT decodedJWT = JWT.require(algorithm).withIssuer(this.userGenerator).build().verify(token);
			return decodedJWT;

		} catch (JWTDecodeException e) {

			throw new InvalidTokenException("Invalid token format.");

		} catch (TokenExpiredException e) {

			throw new InvalidTokenException("Token has expired.");

		} catch (SignatureVerificationException e) {

			throw new InvalidTokenException("Invalid token signature.");

		} catch (AlgorithmMismatchException e) {

			throw new InvalidTokenException("Algorithm mismatch.");

		} catch (JWTVerificationException e) {

			throw new InvalidTokenException("Token verification failed.");

		}
	}

	/**
	 * Extracts the username from the decoded JWT token.
	 *
	 * @param decodedJWT the decoded JWT token
	 * @return the username extracted from the token
	 */
	public String extractUsername(DecodedJWT decodedJWT) {

		return decodedJWT.getSubject();
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
