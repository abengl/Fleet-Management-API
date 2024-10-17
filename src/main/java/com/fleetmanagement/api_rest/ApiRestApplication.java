package com.fleetmanagement.api_rest;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;

/**
 * The entry point of the Fleet Management REST API application.
 * <p>
 * This class contains the main method that is used to launch the Spring Boot application.
 * It is annotated with {@link SpringBootApplication}, which enables auto-configuration,
 * component scanning, and configuration properties scanning.
 * </p>
 *
 * <p>
 * This application is configured to run as a standalone Java application using Spring Boot.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 *     java -jar api-rest-application.jar
 * </pre>
 * </p>
 *
 * @author Alessandra Godoy
 * @version 1.0
 * @since 2024-08-19
 */
@SpringBootApplication
public class ApiRestApplication {

	/**
	 * The main method which serves as the entry point of the Spring Boot application.
	 * It launches the application by invoking {@link SpringApplication#run(Class, String[])}.
	 *
	 * @param args command-line arguments passed to the application
	 */
	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.configure().directory("src/main/resources").load();

		System.setProperty("DB_URL", Objects.requireNonNull(dotenv.get("DB_URL")));
		System.setProperty("DB_USERNAME", Objects.requireNonNull(dotenv.get("DB_USERNAME")));
		System.setProperty("DB_PASSWORD", Objects.requireNonNull(dotenv.get("DB_PASSWORD")));
		System.setProperty("JWT_PRIVATE_KEY", Objects.requireNonNull(dotenv.get("JWT_PRIVATE_KEY")));
		System.setProperty("JWT_USER_GENERATOR", Objects.requireNonNull(dotenv.get("JWT_USER_GENERATOR")));
		System.setProperty("JWT_EXPIRATION_TIME", Objects.requireNonNull(dotenv.get("JWT_EXPIRATION_TIME")));
		System.setProperty("EMAIL_USERNAME", Objects.requireNonNull(dotenv.get("EMAIL_USERNAME")));
		System.setProperty("EMAIL_PASSWORD", Objects.requireNonNull(dotenv.get("EMAIL_PASSWORD")));
		System.setProperty("EMAIL_HOST", Objects.requireNonNull(dotenv.get("EMAIL_HOST")));
		System.setProperty("EMAIL_PORT", Objects.requireNonNull(dotenv.get("EMAIL_PORT")));

		SpringApplication.run(ApiRestApplication.class, args);
	}

}
