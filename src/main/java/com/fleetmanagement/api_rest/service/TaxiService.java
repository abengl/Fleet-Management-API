package com.fleetmanagement.api_rest.service;

import com.fleetmanagement.api_rest.model.Taxi;
import com.fleetmanagement.api_rest.repository.TaxiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for handling business logic related to {@link Taxi} entities.
 * This class provides methods to retrieve taxi records from the database
 * with and without pagination, as well as filtering based on ID and license plate.
 * <p>
 * The {@link TaxiService} class interacts with the {@link TaxiRepository}
 * to perform database operations and encapsulates the logic for querying
 * taxi records based on different criteria.
 * <p>
 * Example usage:
 * <pre>
 *     List<Taxi> allTaxis = taxiService.getAllTaxis();
 *     Page<Taxi> paginatedTaxis = taxiService.getAllTaxis(PageRequest.of(0, 10));
 *     Page<Taxi> filteredTaxis = taxiService.getTaxisByFilters(1, "ABC-123", PageRequest.of(0, 10));
 * </pre>
 * </p>
 *
 * @see TaxiRepository
 * @see Taxi
 */
@Service
public class TaxiService {

	private final TaxiRepository taxiRepository;

	/**
	 * Constructor for {@link TaxiService} that injects the {@link TaxiRepository}.
	 *
	 * @param taxiRepository the repository used to perform CRUD operations on {@link Taxi} entities
	 */
	@Autowired
	public TaxiService(TaxiRepository taxiRepository) {
		this.taxiRepository = taxiRepository;
	}

	/**
	 * Retrieves all {@link Taxi} entities from the database without pagination.
	 *
	 * @return a {@link List} of all {@link Taxi} entities
	 */
	public List<Taxi> getAllTaxis() {
		return taxiRepository.findAll();
	}

	/**
	 * Retrieves all {@link Taxi} entities from the database with pagination.
	 *
	 * @param pageable the pagination information
	 * @return a {@link Page} of {@link Taxi} entities
	 */
	public Page<Taxi> getAllTaxis(Pageable pageable) {
		return taxiRepository.findAll(pageable);
	}

	/**
	 * Retrieves {@link Taxi} entities based on the provided ID and/or license plate,
	 * with pagination. If both ID and plate are provided, the results will be filtered
	 * by both. If either is {@code null}, that filter will be ignored.
	 *
	 * @param id       the ID of the taxi to filter by (can be {@code null})
	 * @param plate    the license plate of the taxi to filter by (can be {@code null})
	 * @param pageable the pagination information
	 * @return a {@link Page} of {@link Taxi} entities matching the criteria
	 */
	public Page<Taxi> getTaxisByFilters(Integer id, String plate, Pageable pageable) {
		return taxiRepository.findByIdOrPlate(id, plate, pageable);
	}
}
