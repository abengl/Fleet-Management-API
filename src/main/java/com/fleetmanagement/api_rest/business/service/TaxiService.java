package com.fleetmanagement.api_rest.business.service;

import com.fleetmanagement.api_rest.business.exception.InvalidLimitException;
import com.fleetmanagement.api_rest.business.exception.ValueNotFoundException;
import com.fleetmanagement.api_rest.presentation.dto.TaxiDTO;
import com.fleetmanagement.api_rest.presentation.mapper.TaxiMapper;
import com.fleetmanagement.api_rest.persistence.entity.Taxi;
import com.fleetmanagement.api_rest.persistence.repository.TaxiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaxiService {

	private static final Logger logger = LoggerFactory.getLogger(TaxiService.class);

	private final TaxiRepository taxiRepository;
	private final TaxiMapper taxiMapper;

	@Autowired
	public TaxiService(TaxiRepository taxiRepository, TaxiMapper taxiMapper) {
		this.taxiRepository = taxiRepository;
		this.taxiMapper = taxiMapper;
	}

	public List<TaxiDTO> getTaxis(String plate, int page, int limit) {
		if (page < 0) {
			throw new InvalidLimitException("Page number cannot be negative");
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
				throw new ValueNotFoundException("No taxis found with plate containing: " + plate);
			}
		}

		List<TaxiDTO> taxiDTOList = taxisPage.stream()
				.map(taxiMapper::toTaxiDTO)
				.collect(Collectors.toList());

		logger.debug("Returning {} taxis", taxiDTOList.size());
		return taxiDTOList;
	}
}
