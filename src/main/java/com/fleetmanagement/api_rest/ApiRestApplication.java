package com.fleetmanagement.api_rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
		SpringApplication.run(ApiRestApplication.class, args);
	}

}
