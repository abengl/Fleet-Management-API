package com.fleetmanagement.api_rest.persistence.repository;

import com.fleetmanagement.api_rest.persistence.entity.Taxi;
import com.fleetmanagement.api_rest.persistence.entity.Trajectory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TrajectoryRepositoryTest {

	@Autowired
	TrajectoryRepository trajectoryRepository;

	@Autowired
	TaxiRepository taxiRepository;

	@BeforeEach
	public void setUp() throws ParseException {
		// Arrange
		Taxi taxi1 = new Taxi(1, "ABC-123");
		Taxi taxi2 = new Taxi(2, "PQR-456");

		SimpleDateFormat formatStringToDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);

		Date date1 = formatStringToDate.parse("01-01-2024 10:30:15");
		Date date2 = formatStringToDate.parse("02-02-2024 14:45:20");
		Date date3 = formatStringToDate.parse("01-01-2024 08:15:30");
		Date date4 = formatStringToDate.parse("28-02-2024 20:00:05");


		double longitude = 100.00;
		double latitude = -100.00;

		List<Trajectory> trajectories = Arrays.asList(
				new Trajectory(10, taxi1, date1, latitude, longitude),
				new Trajectory(20, taxi2, date2, latitude, longitude),
				new Trajectory(30, taxi1, date3, latitude, longitude),
				new Trajectory(40, taxi2, date4, latitude, longitude));

		trajectoryRepository.saveAll(trajectories);
	}

	@Test
	@DisplayName("Testing method existsById() - It should return a boolean")
	public void existsByIdTest() {
		boolean expected1 = taxiRepository.existsById(1);
		boolean expected2 = taxiRepository.existsById(5);

		assertThat(expected1).as("Taxi with ID 1 should exist.").isTrue();
		assertThat(expected2).as("Taxi with ID 5 should not exist.").isFalse();
	}

	@Test
	@DisplayName("Testing method findByTaxiId_IdAndDate() - it should return a page with the matching trajectories")
	public void findByTaxiIdAndDateTest() throws ParseException {

		SimpleDateFormat formatStringToDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		Date date = formatStringToDate.parse("01-01-2024");

		Pageable pageable = PageRequest.of(0, 4);

		// Act
		Page<Trajectory> trajectoriesPage = trajectoryRepository.findByTaxiId_IdAndDate(1, date, pageable);

		// Assert
		assertThat(trajectoriesPage).isNotNull();
		assertThat(trajectoriesPage.getTotalElements()).as("Total elements should be 2").isEqualTo(2);
		assertThat(trajectoriesPage.getContent().get(0).getId()).as("First element should have ID 10").isEqualTo(10);
		assertThat(trajectoriesPage.getContent().get(1).getId()).as("Second element should have ID 30").isEqualTo(30);
	}

	@Test
	@DisplayName("Testing method findLatestLocations() - it should return a page with the latest trajectories")
	public void findLatestLocationsTest() {

		Pageable pageable = PageRequest.of(0, 4);

		// Act
		Page<Trajectory> trajectoriesPage = trajectoryRepository.findLatestLocations(pageable);

		// Assert
		assertThat(trajectoriesPage).isNotNull();
		assertThat(trajectoriesPage.getTotalElements()).as("Total elements should be 2").isEqualTo(2);
		assertThat(trajectoriesPage.getContent().get(0).getId()).as("First element should have ID 10").isEqualTo(10);
		assertThat(trajectoriesPage.getContent().get(1).getId()).as("Second element should have ID 40").isEqualTo(40);
	}
}
