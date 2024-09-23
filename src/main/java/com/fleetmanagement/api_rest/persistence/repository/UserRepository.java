package com.fleetmanagement.api_rest.persistence.repository;

import com.fleetmanagement.api_rest.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for accessing UserEntity data from the database.
 * Extends JpaRepository to provide CRUD operations.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	/**
	 * Checks if a UserEntity exists by its email.
	 *
	 * @param email the email of the UserEntity
	 * @return true if a UserEntity with the given email exists, false otherwise
	 */
	boolean existsUserByEmail(String email);

	/**
	 * Finds a UserEntity by its email.
	 *
	 * @param email the email to search for
	 * @return an Optional containing the UserEntity if found, or empty if not found
	 */
	Optional<UserEntity> findByEmail(String email);

}