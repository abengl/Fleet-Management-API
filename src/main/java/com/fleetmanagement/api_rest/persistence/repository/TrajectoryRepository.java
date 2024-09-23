package com.fleetmanagement.api_rest.persistence.repository;

import com.fleetmanagement.api_rest.persistence.entity.TrajectoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Repository interface for accessing TrajectoryEntity data from the database.
 * Extends JpaRepository to provide CRUD operations.
 */
@Repository
public interface TrajectoryRepository extends JpaRepository<TrajectoryEntity, Integer> {

	/**
	 * Finds TrajectoryEntities by taxi ID and date.
	 * <p>
	 * This query retrieves all trajectory records for a specific taxi on a given date.
	 * The query uses the SQL FUNCTION 'DATE' to ensure that the date comparison is done
	 * at the date level, ignoring the time part.
	 *
	 * @param taxiId   the ID of the taxi
	 * @param date     the date to search for
	 * @param pageable the pagination information
	 * @return a Page of TrajectoryEntities matching the search criteria
	 */
	@Query("SELECT t FROM TrajectoryEntity t WHERE t.taxiId.id = :taxiId AND FUNCTION('DATE', t.date) = :date")
	Page<TrajectoryEntity> findByTaxiId_IdAndDate(@Param("taxiId") Integer taxiId, @Param("date") Date date,
												  Pageable pageable);

	/**
	 * Finds the latest locations of all taxis.
	 * <p>
	 * This query retrieves the most recent trajectory record for each taxi. It uses a subquery
	 * to find the maximum date for each taxi and then selects the corresponding trajectory record.
	 *
	 * @param pageable the pagination information
	 * @return a Page of the latest TrajectoryEntities for each taxi
	 */
	@Query("SELECT t FROM TrajectoryEntity t WHERE t.date = (SELECT MAX(t2.date) FROM TrajectoryEntity t2 WHERE t2" +
			".taxiId.id = t.taxiId.id)")
	Page<TrajectoryEntity> findLatestLocations(Pageable pageable);

}