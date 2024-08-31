package com.fleetmanagement.api_rest.controller;

import com.fleetmanagement.api_rest.model.Taxi;
import com.fleetmanagement.api_rest.service.TaxiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing {@link Taxi} entities.
 * <p>
 * This controller provides endpoints for retrieving taxi records from the database,
 * with support for optional filtering by ID and license plate, as well as pagination.
 * </p>
 *
 * <p>
 * The base URI for accessing these endpoints is {@code /taxis}.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 *     GET /taxis?page=1&limit=10
 *     GET /taxis?id=1&plate=ABC-123&page=1&limit=10
 * </pre>
 * </p>
 *
 * @see TaxiService
 * @see Taxi
 */
@RestController
@RequestMapping("/taxis")
public class TaxiController {
	private final TaxiService taxiService;

	/**
	 * Constructs a {@link TaxiController} with the specified {@link TaxiService}.
	 *
	 * @param taxiService the service layer that handles business logic for {@link Taxi} entities
	 */
	@Autowired
	public TaxiController(TaxiService taxiService) {
		this.taxiService = taxiService;
	}

	/**
	 * Retrieves a list of {@link Taxi} entities with optional filtering by ID and license plate,
	 * and supports pagination.
	 * <p>
	 * If both ID and plate are provided, the results will be filtered by both. If either is
	 * {@code null}, that filter will be ignored.
	 * </p>
	 *
	 * @param id    the ID of the taxi to filter by (optional)
	 * @param plate the license plate of the taxi to filter by (optional)
	 * @param page  the page number to retrieve (default is 1)
	 * @param limit the number of records per page (default is 10)
	 * @return a {@link ResponseEntity} containing a list of {@link Taxi} entities that match the criteria
	 */
	@GetMapping
	public ResponseEntity<List<Taxi>> getAllTaxis(@RequestParam(name = "id", required = false) Integer id,
												  @RequestParam(name = "plate", required = false) String plate,
												  @RequestParam(name = "page", defaultValue = "1") int page,
												  @RequestParam(name = "limit", defaultValue = "10") int limit) {
		Pageable pageable = PageRequest.of(page, limit);
		Page<Taxi> pageTaxis = taxiService.getTaxisByFilters(id, plate, pageable);
		return ResponseEntity.ok(pageTaxis.getContent());
	}

}
