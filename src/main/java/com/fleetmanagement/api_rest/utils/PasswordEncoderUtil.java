package com.fleetmanagement.api_rest.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {
	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	public static String encodePassword(String rawPassword) {
		return encoder.encode(rawPassword);
	}

}
