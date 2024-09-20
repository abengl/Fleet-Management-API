package com.fleetmanagement.api_rest.persistence.repository;

import com.fleetmanagement.api_rest.persistence.entity.UserEntity;
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
		List<UserEntity> userEntities = Arrays.asList(
				UserEntity.builder().name("User 1").email("user1@example.com").password("password123").build(),
				UserEntity.builder().name("User 2").email("user2@example.com").password("password123").build(),
				UserEntity.builder().name("User 3").email("user3@example.com").password("password123").build(),
				UserEntity.builder().name("User 4").email("user4@example.com").password("password123").build()
		);

		userRepository.saveAll(userEntities);
		System.out.println("Data successfully added:");
		userRepository.findAll().forEach(System.out::println);
	}

	@Test
	@DisplayName("Testing method findAll() - It should return a page with all users")
	public void findAllTest() {
		// Act
		Pageable pageable = PageRequest.of(0, 5);
		Page<UserEntity> userPage = userRepository.findAll(pageable);

		System.out.println("T1 - User page:");
		userPage.getContent().forEach(System.out::println);

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
		UserEntity
				newUserEntity =
				UserEntity.builder().name("User 5").email("user5@example.com").password("password123").build();

		// Act
		UserEntity expected = userRepository.save(newUserEntity);

		System.out.println("T3 - New user added");
		System.out.println(newUserEntity);

		// Assert
		assertThat(expected.getName()).as("New user name should be User 5").isEqualTo("User 5");
		assertThat(userRepository.existsUserByEmail("user5@example.com")).as("New user exists in the database")
				.isTrue();
		assertThat(userRepository.count()).as("User count should be 5 after saving a new user").isEqualTo(5);
	}

	@Test
	@DisplayName("Testing method findById() - It should return the entity")
	void findByIdTest() {
		UserEntity
				newUserEntity =
				UserEntity.builder().name("User 5").email("user5@example.com").password("password123").build();
		userRepository.save(newUserEntity);

		System.out.println("T4 - New user added");
		System.out.println(newUserEntity);

		// Act
		Optional<UserEntity> expected1 = userRepository.findById(newUserEntity.getId());
		Optional<UserEntity> expected2 = userRepository.findById(100);

		// Assert
		assertThat(expected1).as("User should be retrievable by ID").isPresent();
		assertThat(expected2).as("User is not reached by ID").isEmpty();
	}

	@Test
	@DisplayName("Testing method delete() - It deletes the entity without return value")
	void deleteTest() {
		UserEntity
				newUserEntity =
				UserEntity.builder().name("User 5").email("user5@example.com").password("password123").build();
		userRepository.save(newUserEntity);

		System.out.println("T5 - New user added");
		System.out.println(newUserEntity);

		// Act
		userRepository.delete(newUserEntity);
		Optional<UserEntity> expected1 = userRepository.findById(newUserEntity.getId());

		// Assert
		assertThat(expected1).as("User should be empty").isEmpty();
		assertThat(userRepository.count()).as("User count should be 4 after deleting the user").isEqualTo(4);
	}

}