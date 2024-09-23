package com.fleetmanagement.api_rest.presentation.controller;

import com.fleetmanagement.api_rest.business.service.TaxiService;
import com.fleetmanagement.api_rest.presentation.dto.TaxiDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for handling taxi-related requests.
 */
@RestController
@RequestMapping("/taxis")
public class TaxiController {

	private final TaxiService taxiService;

	@Autowired
	public TaxiController(TaxiService taxiService) {
		this.taxiService = taxiService;
	}

	/**
	 * Retrieves a list of taxis based on the provided plate number, page, and limit.
	 *
	 * @param plate the plate number of the taxi (optional)
	 * @param page  the page number for pagination (default is 0)
	 * @param limit the maximum number of results per page (default is 10)
	 * @return a ResponseEntity containing a list of TaxiDTO objects
	 */
	@GetMapping
	public ResponseEntity<List<TaxiDTO>> getAllTaxisByPlate(
			@RequestParam(name = "plate", required = false) String plate,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "limit", defaultValue = "10") int limit) {

		List<TaxiDTO> taxis = taxiService.getTaxis(plate, page, limit);
		return ResponseEntity.ok(taxis);
	}
}

