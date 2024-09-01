package com.fleetmanagement.api_rest.service;

import com.fleetmanagement.api_rest.dto.TrajectoryDTO;
import com.fleetmanagement.api_rest.exception.InvalidLimitException;
import com.fleetmanagement.api_rest.exception.InvalidPageException;
import com.fleetmanagement.api_rest.exception.RequiredParameterException;
import com.fleetmanagement.api_rest.exception.ValueNotFoundException;
import com.fleetmanagement.api_rest.mapper.TrajectoryMapper;
import com.fleetmanagement.api_rest.model.Trajectory;
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
	private final TrajectoryMapper trajectoryMapper;

	@Autowired
	public TrajectoryService(TrajectoryRepository trajectoryRepository, TrajectoryMapper trajectoryMapper) {
		this.trajectoryRepository = trajectoryRepository;
		this.trajectoryMapper = trajectoryMapper;
	}

	public List<TrajectoryDTO> getTrajectories(Integer taxiId, String dateString, int page, int limit)
			throws ParseException {
		if (page < 0) {
			throw new InvalidPageException("Page number cannot be negative");
		}
		if (limit <= 0) {
			throw new InvalidLimitException("Limit must be greater than zero");
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		Date date = sdf.parse(dateString);

		Pageable pageable = PageRequest.of(page, limit);
		Page<Trajectory> trajectoryPage;

		if (taxiId == null) {
			throw new RequiredParameterException("No taxi ID provided.");
		}

		if (date == null) {
			throw new RequiredParameterException("No date is provided.");
		}


		trajectoryPage = trajectoryRepository.findByTaxiId_IdAndDate(taxiId, date, pageable);

		if (trajectoryPage.isEmpty()) {
			throw new ValueNotFoundException("No taxis found with taxiId: " + taxiId);
		}

		return trajectoryPage.stream()
				.map(trajectoryMapper::toTrajectoryDTO)
				.collect(Collectors.toList());
	}
}
