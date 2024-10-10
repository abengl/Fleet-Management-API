package com.fleetmanagement.api_rest;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;

@SpringBootTest
@ActiveProfiles("test")
class ApiRestApplicationTest {
	@BeforeAll
	static void setup() {

		Dotenv dotenv = Dotenv.configure().directory("src/main/resources").load();

		System.setProperty("TEST_DB_URL", Objects.requireNonNull(dotenv.get("TEST_DB_URL")));
		System.setProperty("TEST_DB_USERNAME", Objects.requireNonNull(dotenv.get("TEST_DB_USERNAME")));
		System.setProperty("TEST_DB_PASSWORD", Objects.requireNonNull(dotenv.get("TEST_DB_PASSWORD")));
		System.setProperty("TEST_JWT_PRIVATE_KEY", Objects.requireNonNull(dotenv.get("TEST_JWT_PRIVATE_KEY")));
		System.setProperty("TEST_JWT_USER_GENERATOR", Objects.requireNonNull(dotenv.get("TEST_JWT_USER_GENERATOR")));
		System.setProperty("TEST_JWT_EXPIRATION_TIME", Objects.requireNonNull(dotenv.get("TEST_JWT_EXPIRATION_TIME")));
	}
	@Test
	public void contextLoads() {
	}

}