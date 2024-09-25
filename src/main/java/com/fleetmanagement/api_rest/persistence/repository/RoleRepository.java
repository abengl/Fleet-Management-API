package com.fleetmanagement.api_rest.persistence.repository;

import com.fleetmanagement.api_rest.persistence.entity.RoleEntity;
import com.fleetmanagement.api_rest.persistence.entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for accessing RoleEntity data from the database.
 * Extends JpaRepository to provide CRUD operations.
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

	/**
	 * Finds a RoleEntity by its RoleEnum.
	 *
	 * @param roleName the role enum to search for
	 * @return an Optional containing the RoleEntity if found, or empty if not found
	 */
	Optional<RoleEntity> findByRoleEnum(RoleEnum roleName);

}
