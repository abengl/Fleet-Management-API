package com.fleetmanagement.api_rest.business.service;

import com.fleetmanagement.api_rest.business.exception.ValueNotFoundException;
import com.fleetmanagement.api_rest.persistence.entity.TaxiEntity;
import com.fleetmanagement.api_rest.persistence.repository.TaxiRepository;
import com.fleetmanagement.api_rest.presentation.dto.TaxiDTO;
import com.fleetmanagement.api_rest.utils.mapper.TaxiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaxiService {

	private final TaxiRepository taxiRepository;
	private final TaxiMapper taxiMapper;

	@Autowired
	public TaxiService(TaxiRepository taxiRepository, TaxiMapper taxiMapper) {
		this.taxiRepository = taxiRepository;
		this.taxiMapper = taxiMapper;
	}

	public List<TaxiDTO> getTaxis(String plate, int page, int limit) {
		if (page < 0) {
			throw new InvalidParameterException("Page number cannot be negative");
		}
		if (limit <= 0) {
			throw new InvalidParameterException("Limit must be greater than zero");
		}

		Pageable pageable = PageRequest.of(page, limit);
		Page<TaxiEntity> taxisPage;

		if (plate == null || plate.isEmpty()) {
			taxisPage = taxiRepository.findAll(pageable);
		} else {
			taxisPage = taxiRepository.findByPlateContainingIgnoreCase(plate, pageable);
			if (taxisPage.isEmpty()) {
				throw new ValueNotFoundException("No taxis found with plate containing: " + plate);
			}
		}

		return taxisPage.stream().map(taxiMapper::toTaxiDTO).collect(Collectors.toList());

	}
}
