package com.fleetmanagement.api_rest.persistence.repository;

import com.fleetmanagement.api_rest.persistence.entity.TaxiEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing TaxiEntity data from the database.
 * Extends JpaRepository to provide CRUD operations.
 */
@Repository
public interface TaxiRepository extends JpaRepository<TaxiEntity, Integer> {

	/**
	 * Checks if a TaxiEntity exists by its ID.
	 *
	 * @param id the ID of the TaxiEntity
	 * @return true if a TaxiEntity with the given ID exists, false otherwise
	 */
	@Override
	boolean existsById(Integer id);

	/**
	 * Finds TaxiEntities whose plate contains the given string, ignoring case.
	 *
	 * @param plate    the string to search for in the plate
	 * @param pageable the pagination information
	 * @return a Page of TaxiEntities matching the search criteria
	 */
	Page<TaxiEntity> findByPlateContainingIgnoreCase(String plate, Pageable pageable);
}