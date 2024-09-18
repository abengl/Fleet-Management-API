package com.fleetmanagement.api_rest.business.service;


import com.fleetmanagement.api_rest.business.exception.InvalidFormatException;
import com.fleetmanagement.api_rest.business.exception.ValueNotFoundException;
import com.fleetmanagement.api_rest.persistence.entity.Taxi;
import com.fleetmanagement.api_rest.persistence.entity.Trajectory;
import com.fleetmanagement.api_rest.persistence.repository.TaxiRepository;
import com.fleetmanagement.api_rest.persistence.repository.TrajectoryRepository;
import com.fleetmanagement.api_rest.presentation.dto.LatestTrajectoryDTO;
import com.fleetmanagement.api_rest.presentation.dto.TrajectoryDTO;
import com.fleetmanagement.api_rest.utils.mapper.LatestTrajectoryMapper;
import com.fleetmanagement.api_rest.utils.mapper.TrajectoryMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrajectoryServiceTest {

	@Mock
	TrajectoryRepository trajectoryRepository;
	@Mock
	TaxiRepository taxiRepository;
	@Mock
	TrajectoryMapper trajectoryMapper;
	@Mock
	LatestTrajectoryMapper latestTrajectoryMapper;
	@InjectMocks
	TrajectoryService trajectoryService;

	String dateString = "2024-01-01";
	double latitude = 100.00;
	double longitude = -100.00;
	private int page = 0;
	private int limit = 10;

	@Test
	@DisplayName("Testing getTrajectories() without taxiId - It throws a RequiredParameterException")
	public void getTrajectoriesWithoutTaxiIdTest() {
		// Arrange
		Integer undefinedId = null;

		// Act & Assert
		InvalidParameterException exception = assertThrows(InvalidParameterException.class,
				() -> trajectoryService.getTrajectories(undefinedId, dateString, page, limit));
		assertEquals("Missing taxiId value.", exception.getMessage());
	}

	@Test
	@DisplayName("Testing getTrajectories() with non-existent taxiId - It throws a ValueNotFoundException")
	public void getTrajectoriesNonExistentTaxiIdTest() {
		// Arrange
		Integer invalidId = 1111111;

		when(taxiRepository.existsById(invalidId)).thenReturn(false);

		// Act & Assert
		ValueNotFoundException exception = assertThrows(ValueNotFoundException.class,
				() -> trajectoryService.getTrajectories(invalidId, dateString, page, limit));
		assertEquals("Taxi ID " + invalidId + " not found.", exception.getMessage());
	}

	@Test
	@DisplayName("Testing getTrajectories() without date parameter - It throws a RequiredParameterException")
	public void getTrajectoriesWithoutDateTest() {
		// Arrange
		Integer taxiId = 1;
		String emptyDate = "";

		when(taxiRepository.existsById(taxiId)).thenReturn(true);

		// Act & Assert
		InvalidParameterException exception = assertThrows(InvalidParameterException.class,
				() -> trajectoryService.getTrajectories(taxiId, emptyDate, page, limit));
		assertEquals("Missing date value.", exception.getMessage());
	}

	@Test
	@DisplayName("Testing getTrajectories() with an invalid date parameter - It throws a InvalidFormatException")
	public void getTrajectoriesInvalidDateTest() {
		// Arrange
		Integer taxiId = 1;
		String invalidDate = "123xyz";

		when(taxiRepository.existsById(taxiId)).thenReturn(true);

		// Act & Assert
		InvalidFormatException exception = assertThrows(InvalidFormatException.class,
				() -> trajectoryService.getTrajectories(taxiId, invalidDate, page, limit));
		assertEquals("Incorrect date value: " + invalidDate, exception.getMessage());
	}

	@Test
	@DisplayName("Testing getTrajectories() with valid parameters - It returns a Trajectory page")
	public void getTrajectoriesTest() throws ParseException {
		// Arrange
		Integer taxiId = 1;

		SimpleDateFormat formatStringToDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		Date date = formatStringToDate.parse(dateString);

		Taxi taxi1 = Taxi.builder().plate("ABC-123").build();

		List<Trajectory> trajectories =
				Arrays.asList(
						Trajectory.builder().taxiId(taxi1).date(date).latitude(latitude).longitude(longitude).build());

		Pageable pageable = PageRequest.of(page, limit);
		Page<Trajectory> trajectoriesPage = new PageImpl<>(trajectories, pageable, trajectories.size());

		TrajectoryDTO trajectory = TrajectoryDTO.builder()
				.plate("ABC-123")
				.taxiId(taxiId)
				.date(dateString)
				.latitude(latitude)
				.longitude(longitude).build();

		when(taxiRepository.existsById(taxiId)).thenReturn(true);
		when(trajectoryRepository.findByTaxiId_IdAndDate(taxiId, date, pageable)).thenReturn(trajectoriesPage);
		when(trajectoryMapper.toTrajectoryDTO(any(Trajectory.class))).thenReturn(trajectory);

		// Act
		List<TrajectoryDTO> trajectoryDTOList = trajectoryService.getTrajectories(taxiId, dateString, page, limit);

		// Assert
		assertNotNull(trajectoryDTOList);
		assertEquals(1, trajectoryDTOList.size());
		verify(taxiRepository).existsById(taxiId); // Called once
		verify(trajectoryRepository).findByTaxiId_IdAndDate(taxiId, date, pageable); // Called wit parameters
		verify(trajectoryMapper).toTrajectoryDTO(any(Trajectory.class)); // Called once
	}


	@Test
	@DisplayName("Testing getLatestTrajectories() with valid parameter - It returns a Trajectory page")
	public void getLatestTrajectoriesTest() throws ParseException {
		// Arrange
		Integer taxiId = 1;

		SimpleDateFormat formatStringToDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		Date date = formatStringToDate.parse(dateString);

		Taxi taxi1 = Taxi.builder().plate("ABC-123").build();

		List<Trajectory> trajectories =
				Arrays.asList(
						Trajectory.builder().taxiId(taxi1).date(date).latitude(latitude).longitude(longitude).build());

		Pageable pageable = PageRequest.of(page, limit);
		Page<Trajectory> trajectoriesPage = new PageImpl<>(trajectories, pageable, trajectories.size());

		LatestTrajectoryDTO latestTrajectory = LatestTrajectoryDTO.builder()
				.taxiId(taxiId)
				.plate("ABC-123")
				.date(dateString)
				.latitude(latitude)
				.longitude(longitude).build();

		// Mock the repository and mapper
		when(trajectoryRepository.findLatestLocations(pageable)).thenReturn(trajectoriesPage);
		when(latestTrajectoryMapper.toLatestTrajectoryDTO(any(Trajectory.class))).thenReturn(
				latestTrajectory);

		// Act
		List<LatestTrajectoryDTO> latestTrajectoriesDTOList = trajectoryService.getLatestTrajectories(page, limit);

		// Assert
		assertNotNull(latestTrajectoriesDTOList);
		assertEquals(1, latestTrajectoriesDTOList.size());
		verify(trajectoryRepository).findLatestLocations(pageable); // Called wit parameters
		verify(latestTrajectoryMapper).toLatestTrajectoryDTO(any(Trajectory.class)); // Called once
	}
}

