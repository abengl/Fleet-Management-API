package com.fleetmanagement.api_rest.persistence.repository;

import com.fleetmanagement.api_rest.business.exception.ValueNotFoundException;
import com.fleetmanagement.api_rest.persistence.entity.RoleEntity;
import com.fleetmanagement.api_rest.persistence.entity.RoleEnum;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	private UserEntity newUserEntity;

	@BeforeEach
	public void setUp() {
		String encodedPassword = "$2a$12$6ET83xFaVLihfB4fTrzX.eznPIIvPeWluqf7G/v0eXTuYaQNrhRne";

		RoleEntity role = roleRepository.findByRoleEnum(RoleEnum.USER)
				.orElseThrow(() -> new ValueNotFoundException("Role doesn't exist."));

		newUserEntity =
				UserEntity.builder().name("user").email("user@example.com").password(encodedPassword).isEnabled(true)
						.accountNonExpired(true).accountNonLocked(true).accountNonExpired(true).build();
		newUserEntity.setRole(role);
		userRepository.save(newUserEntity);
	}

	@Test
	@DisplayName("Verify User Data")
	public void verifyTestData() {
		userRepository.findAll().forEach(System.out::println);
		assertThat(userRepository.findAll()).hasSize(2);
	}

	@Test
	@DisplayName("Testing method findAll() - It should return a page with all users")
	public void findAllTest() {
		// Act
		Pageable pageable = PageRequest.of(0, 5);
		Page<UserEntity> userPage = userRepository.findAll(pageable);

		// Assert
		assertThat(userPage).isNotNull();
		assertThat(userPage.getTotalPages()).as("Total pages should be 1").isEqualTo(1);
		assertThat(userPage.getTotalElements()).as("Total elements should be 1").isEqualTo(2);
		assertThat(userPage.getContent().get(0).getName()).as("The first name should be admin").isEqualTo("admin");
	}

	@Test
	@DisplayName("Testing method existsUserByEmail() - It should return boolean")
	void existsUserByEmailTest() {
		// Act
		boolean expected1 = userRepository.existsUserByEmail("admin@test.com");
		boolean expected2 = userRepository.existsUserByEmail("user000@example.com");

		// Assert
		assertThat(expected1).as("User with email admin@test.com exists.").isTrue();
		assertThat(expected2).as("User with email user000@example.com doesn't exist.").isFalse();
	}

	@Test
	@DisplayName("Testing method save() - It should return boolean")
	void saveTest() {

		// Act
		UserEntity expected = userRepository.save(newUserEntity);

		// Assert
		assertThat(expected.getName()).as("New user name should be user").isEqualTo("user");
		assertThat(userRepository.existsUserByEmail("user@example.com")).as("New user exists in the database")
				.isTrue();
	}

	@Test
	@DisplayName("Testing method findById() - It should return the entity")
	void findByIdTest() {

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

		// Act
		userRepository.delete(newUserEntity);
		Optional<UserEntity> expected1 = userRepository.findById(newUserEntity.getId());

		// Assert
		assertThat(expected1).as("User should be empty").isEmpty();
		assertThat(userRepository.count()).as("User count should be 1 after deleting the user").isEqualTo(1);
	}

}