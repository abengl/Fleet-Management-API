package com.fleetmanagement.api_rest.business.service;

import com.fleetmanagement.api_rest.business.exception.InvalidFormatException;
import com.fleetmanagement.api_rest.business.exception.ValueNotFoundException;
import com.fleetmanagement.api_rest.persistence.entity.TrajectoryEntity;
import com.fleetmanagement.api_rest.persistence.repository.TaxiRepository;
import com.fleetmanagement.api_rest.persistence.repository.TrajectoryRepository;
import com.fleetmanagement.api_rest.presentation.dto.LatestTrajectoryDTO;
import com.fleetmanagement.api_rest.presentation.dto.TrajectoryDTO;
import com.fleetmanagement.api_rest.utils.mapper.LatestTrajectoryMapper;
import com.fleetmanagement.api_rest.utils.mapper.TrajectoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Service class for managing Trajectory entities.
 */
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

	/**
	 * Retrieves a paginated list of TrajectoryDTOs for a specific taxi and date.
	 *
	 * @param taxiId     the ID of the taxi
	 * @param dateString the date in "dd-MM-yyyy" format
	 * @param page       the page number to retrieve
	 * @param limit      the number of items per page
	 * @return a list of TrajectoryDTOs
	 * @throws InvalidParameterException if taxiId or dateString is missing, or if page or limit is invalid
	 * @throws ValueNotFoundException    if the taxi ID is not found
	 * @throws InvalidFormatException    if the date format is incorrect
	 */
	public List<TrajectoryDTO> getTrajectories(Integer taxiId, String dateString, int page, int limit) {

		if (taxiId == null) {
			throw new InvalidParameterException("Missing taxiId value.");
		}

		if (!taxiRepository.existsById(taxiId)) {
			throw new ValueNotFoundException("Taxi ID " + taxiId + " not found.");
		}

		if (dateString == null || dateString.isEmpty()) {
			throw new InvalidParameterException("Missing date value.");
		}

		Date date;
		try {
			SimpleDateFormat formatStringToDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
			date = formatStringToDate.parse(dateString);
		} catch (ParseException e) {
			throw new InvalidFormatException("Incorrect date value: " + dateString);
		}

		Pageable pageable = PageRequest.of(page, limit);
		Page<TrajectoryEntity> trajectoryPage = trajectoryRepository.findByTaxiId_IdAndDate(taxiId, date, pageable);

		return trajectoryPage.stream().map(trajectoryMapper::toTrajectoryDTO).collect(Collectors.toList());
	}

	/**
	 * Retrieves a paginated list of the latest TrajectoryDTOs.
	 *
	 * @param page  the page number to retrieve
	 * @param limit the number of items per page
	 * @return a list of LatestTrajectoryDTOs
	 */
	public List<LatestTrajectoryDTO> getLatestTrajectories(int page, int limit) {

		Pageable pageable = PageRequest.of(page, limit);
		Page<TrajectoryEntity> trajectoryPage = trajectoryRepository.findLatestLocations(pageable);

		return trajectoryPage.stream().map(latestTrajectoryMapper::toLatestTrajectoryDTO).collect(Collectors.toList());
	}
}
