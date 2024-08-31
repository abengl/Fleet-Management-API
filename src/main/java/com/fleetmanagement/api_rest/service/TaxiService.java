package com.fleetmanagement.api_rest.service;

import com.fleetmanagement.api_rest.dto.TaxiDTO;
import com.fleetmanagement.api_rest.exception.InvalidLimitException;
import com.fleetmanagement.api_rest.exception.InvalidPageException;
import com.fleetmanagement.api_rest.exception.PlateNotFoundException;
import com.fleetmanagement.api_rest.mapper.TaxiMapper;
import com.fleetmanagement.api_rest.model.Taxi;
import com.fleetmanagement.api_rest.repository.TaxiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaxiService {

	private final TaxiRepository taxiRepository;
	private final TaxiMapper taxiMapper = TaxiMapper.INSTANCE;

	@Autowired
	public TaxiService(TaxiRepository taxiRepository) {
		this.taxiRepository = taxiRepository;
	}

	public List<TaxiDTO> getTaxis(String plate, int page, int limit) {
		if (page < 0) {
			throw new InvalidPageException("Page number cannot be negative");
		}
		if (limit <= 0) {
			throw new InvalidLimitException("Limit must be greater than zero");
		}

		Pageable pageable = PageRequest.of(page, limit);
		Page<Taxi> taxisPage;

		if (plate == null || plate.isEmpty()) {
			taxisPage = taxiRepository.findAll(pageable);
		} else {
			taxisPage = taxiRepository.findByPlateContainingIgnoreCase(plate, pageable);
			if (taxisPage.isEmpty()) {
				throw new PlateNotFoundException("No taxis found with plate containing: " + plate);
			}
		}

		return taxisPage.stream()
				.map(taxiMapper::toTaxiDTO)
				.collect(Collectors.toList());
	}
}
