package com.fleetmanagement.api_rest.controller;

import com.fleetmanagement.api_rest.dto.TaxiDTO;
import com.fleetmanagement.api_rest.service.TaxiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/taxis")
public class TaxiController {

	private static final Logger logger = LoggerFactory.getLogger(TaxiController.class);

	private final TaxiService taxiService;

	@Autowired
	public TaxiController(TaxiService taxiService) {
		this.taxiService = taxiService;
	}

	@GetMapping
	public ResponseEntity<List<TaxiDTO>> getAllTaxisByPlate(
			@RequestParam(name = "plate", required = false) String plate,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "limit", defaultValue = "10") int limit) {

		logger.debug("Received request with plate={}, page={}, limit={}", plate, page, limit);
		List<TaxiDTO> taxis = taxiService.getTaxis(plate, page, limit);
		return ResponseEntity.ok(taxis);
	}
}

