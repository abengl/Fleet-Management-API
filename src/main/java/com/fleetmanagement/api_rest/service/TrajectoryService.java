package com.fleetmanagement.api_rest.service;

import com.fleetmanagement.api_rest.dto.LatestTrajectoryDTO;
import com.fleetmanagement.api_rest.dto.TrajectoryDTO;
import com.fleetmanagement.api_rest.exception.InvalidFormatException;
import com.fleetmanagement.api_rest.exception.RequiredParameterException;
import com.fleetmanagement.api_rest.exception.ValueNotFoundException;
import com.fleetmanagement.api_rest.mapper.LatestTrajectoryMapper;
import com.fleetmanagement.api_rest.mapper.TrajectoryMapper;
import com.fleetmanagement.api_rest.model.Trajectory;
import com.fleetmanagement.api_rest.repository.TaxiRepository;
import com.fleetmanagement.api_rest.repository.TrajectoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class TrajectoryService {
	private final TrajectoryRepository trajectoryRepository;
	private final TaxiRepository taxiRepository;
	private final TrajectoryMapper trajectoryMapper;
	private final LatestTrajectoryMapper latestTrajectoryMapper;

	@Autowired
	public TrajectoryService(TrajectoryRepository trajectoryRepository, TaxiRepository taxiRepository,
							 TrajectoryMapper trajectoryMapper, LatestTrajectoryMapper latestTrajectoryMapper) {
		this.trajectoryRepository = trajectoryRepository;
		this.taxiRepository = taxiRepository;
		this.trajectoryMapper = trajectoryMapper;
		this.latestTrajectoryMapper = latestTrajectoryMapper;
	}

	public List<TrajectoryDTO> getTrajectories(Integer taxiId, String dateString, int page, int limit) {

		if (taxiId == null) {
			throw new RequiredParameterException("Missing ID parameter");
		}

		if (!taxiRepository.existsById(taxiId)) {
			throw new ValueNotFoundException("Taxi ID " + taxiId + " not found");
		}

		if (dateString == null || dateString.isEmpty()) {
			throw new RequiredParameterException("Missing date parameter");
		}

		Date date;
		try {
			SimpleDateFormat formatStringToDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
			date = formatStringToDate.parse(dateString);
		} catch (ParseException e) {
			throw new InvalidFormatException("Invalid date format: " + dateString);
		}

		Pageable pageable = PageRequest.of(page, limit);
		Page<Trajectory> trajectoryPage = trajectoryRepository.findByTaxiId_IdAndDate(taxiId, date, pageable);

		return trajectoryPage.stream().map(trajectoryMapper::toTrajectoryDTO).collect(Collectors.toList());
	}

	public List<LatestTrajectoryDTO> getLatestTrajectories(int page, int limit) {

		Pageable pageable = PageRequest.of(page, limit);
		Page<Trajectory> trajectoryPage = trajectoryRepository.findLatestLocations(pageable);

		return trajectoryPage.stream().map(latestTrajectoryMapper::toLatestTrajectoryDTO).collect(Collectors.toList());
	}


}
