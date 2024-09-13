package com.fleetmanagement.api_rest.persistence.repository;

import com.fleetmanagement.api_rest.persistence.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	void setUp() {
		List<User> users = Arrays.asList(
				new User("User 1", "user1@example.com", "password123"),
				new User("User 2", "user2@example.com", "password123"),
				new User("User 3", "user3@example.com", "password123"),
				new User("User 4", "user4@example.com", "password123")
		);

		userRepository.saveAll(users);
	}

	@Test
	@DisplayName("Testing method findAll() - It should return a page with all users")
	public void findAllTest() {
		// Act
		Pageable pageable = PageRequest.of(0, 5);
		Page<User> userPage = userRepository.findAll(pageable);

		// Assert
		assertThat(userPage).isNotNull();
		assertThat(userPage.getTotalPages()).as("Total pages should be 1").isEqualTo(1);
		assertThat(userPage.getTotalElements()).as("Total elements should be 4").isEqualTo(4);
		assertThat(userPage.getContent().get(0).getName()).as("The first name should be User 1")
				.isEqualTo("User 1");
	}

	@Test
	@DisplayName("Testing method existsUserByEmail() - It should return boolean")
	void existsUserByEmailTest() {
		// Act
		boolean expected1 = userRepository.existsUserByEmail("user3@example.com");
		boolean expected2 = userRepository.existsUserByEmail("user000@example.com");

		// Assert
		assertThat(expected1).as("User with email user3@example.com exists.").isTrue();
		assertThat(expected2).as("User with email user000@example.com doesn't exist.").isFalse();
	}

	@Test
	@DisplayName("Testing method save() - It should return boolean")
	void saveTest() {
		User newUser = new User("User 5", "user5@example.com", "password123");

		// Act
		User expected = userRepository.save(newUser);

		// Assert
		assertThat(expected.getName()).as("New user name should be User 5").isEqualTo("User 5");
		assertThat(userRepository.existsUserByEmail("user5@example.com")).as("New user exists in the database")
				.isTrue();
		assertThat(userRepository.count()).as("User count should be 5 after saving a new user").isEqualTo(5);
	}

	@Test
	@DisplayName("Testing method findById() - It should return the entity")
	void findByIdTest() {
		User newUser = new User("User 5", "user5@example.com", "password123");
		userRepository.save(newUser);

		// Act
		Optional<User> expected1 = userRepository.findById(newUser.getId());
		Optional<User> expected2 = userRepository.findById(100);

		// Assert
		assertThat(expected1).as("User should be retrievable by ID").isPresent();
		assertThat(expected2).as("User is not reached by ID").isEmpty();
	}

	@Test
	@DisplayName("Testing method delete() - It deletes the entity without return value")
	void deleteTest() {
		User newUser = new User("User 5", "user5@example.com", "password123");
		userRepository.save(newUser);
		System.out.println(newUser.getId());
		// Act
		userRepository.delete(newUser);
		Optional<User> expected1 = userRepository.findById(newUser.getId());

		// Assert
		assertThat(expected1).as("User should be empty").isEmpty();
		assertThat(userRepository.count()).as("User count should be 4 after deleting the user").isEqualTo(4);
	}

}