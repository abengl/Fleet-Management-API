package com.fleetmanagement.api_rest.repository;

import com.fleetmanagement.api_rest.model.Taxi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository interface for {@link Taxi} entities, providing basic CRUD operations
 * and custom queries for filtering and pagination. This interface extends
 * {@link JpaRepository}, inheriting several methods to interact with the database
 * without needing to write boilerplate code.
 * <p>
 * The {@link TaxiRepository} interface offers a custom query method
 * {@link #findByIdOrPlate(Integer, String, Pageable)} that allows for
 * filtering {@link Taxi} entities by either ID, plate, or both.
 * <p>
 * Example usage:
 * <pre>
 *     Page<Taxi> taxis = taxiRepository.findByIdOrPlate(1, "ABC-123", PageRequest.of(0, 10));
 * </pre>
 * </p>
 *
 * @see JpaRepository
 * @see Taxi
 */
public interface TaxiRepository extends JpaRepository<Taxi, Integer> {

	/**
	 * Finds a paginated list of {@link Taxi} entities based on the provided ID or plate.
	 * If both parameters are provided, the method will filter by both fields.
	 * If either parameter is {@code null}, it will be ignored in the query.
	 *
	 * @param id       the ID of the taxi to filter by (can be {@code null})
	 * @param plate    the license plate of the taxi to filter by (can be {@code null})
	 * @param pageable the pagination information
	 * @return a {@link Page} of {@link Taxi} entities matching the criteria
	 */
	@Query("SELECT t FROM Taxi t WHERE (:id IS NULL OR t.id = :id) AND (:plate IS NULL OR t.plate = :plate)")
	Page<Taxi> findByIdOrPlate(@Param("id") Integer id, @Param("plate") String plate, Pageable pageable);

	// Other methods to filter by id or plate:
	// Page<Taxi> findByIdAndPlate(Integer id, String plate, Pageable pageable);
	// Page<Taxi> findById(Integer id, Pageable pageable);
	// Page<Taxi> findByPlate(String plate, Pageable pageable);

}
