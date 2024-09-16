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

	private Integer taxiId;

	@BeforeEach
	public void setUp() throws ParseException {
		// Arrange
		Taxi taxi1 = Taxi.builder().plate("ABC-123").build();
		Taxi taxi2 = Taxi.builder().plate("PQR-456").build();

		taxi1 = taxiRepository.save(taxi1);
		taxi2 = taxiRepository.save(taxi2);

		taxiId = taxi1.getId();

		SimpleDateFormat formatStringToDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);

		Date date1 = formatStringToDate.parse("01-01-2024 10:30:15");
		Date date2 = formatStringToDate.parse("02-02-2024 14:45:20");
		Date date3 = formatStringToDate.parse("01-01-2024 06:15:30");
		Date date4 = formatStringToDate.parse("28-02-2024 08:00:05");


		double longitude = 100.00;
		double latitude = -100.00;

		List<Trajectory> trajectories = Arrays.asList(
				Trajectory.builder().taxiId(taxi1).date(date1).latitude(latitude).longitude(longitude).build(),
				Trajectory.builder().taxiId(taxi2).date(date2).latitude(latitude).longitude(longitude).build(),
				Trajectory.builder().taxiId(taxi1).date(date3).latitude(latitude).longitude(longitude).build(),
				Trajectory.builder().taxiId(taxi2).date(date4).latitude(latitude).longitude(longitude).build());

		trajectoryRepository.saveAll(trajectories);
		System.out.println("Data successfully added:");
		trajectoryRepository.findAll().forEach(System.out::println);
	}

	@Test
	@DisplayName("Testing method existsById() - It should return a boolean")
	public void existsByIdTest() {
		boolean expected1 = taxiRepository.existsById(taxiId);
		boolean expected2 = taxiRepository.existsById(55555);

		assertThat(expected1).as("Taxi with ID 1 should exist.").isTrue();
		assertThat(expected2).as("Taxi with ID 55555 should not exist.").isFalse();
	}

	@Test
	@DisplayName("Testing method findByTaxiId_IdAndDate() - it should return a page with the matching trajectories")
	public void findByTaxiIdAndDateTest() throws ParseException {

		SimpleDateFormat formatStringToDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		Date date = formatStringToDate.parse("01-01-2024");

		Pageable pageable = PageRequest.of(0, 4);

		// Act
		Page<Trajectory> trajectoriesPage = trajectoryRepository.findByTaxiId_IdAndDate(taxiId, date, pageable);

		trajectoriesPage.getContent().forEach(System.out::println);

		// Assert
		assertThat(trajectoriesPage).isNotNull();
		assertThat(trajectoriesPage.getTotalElements()).as("Total elements should be 2").isEqualTo(2);
		assertThat(trajectoriesPage.getContent().get(0).getTaxiId().getPlate()).as(
				"First element should have plate ABC-123").isEqualTo("ABC-123");
		assertThat(formatStringToDate.format(trajectoriesPage.getContent().get(1).getDate()))
				.as("Second element should have date 01-01-2024")
				.isEqualTo("01-01-2024");
	}

	@Test
	@DisplayName("Testing method findLatestLocations() - it should return a page with the latest trajectories")
	public void findLatestLocationsTest() {
		SimpleDateFormat formatStringToDate = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.ENGLISH);
		Pageable pageable = PageRequest.of(0, 4);

		// Act
		Page<Trajectory> trajectoriesPage = trajectoryRepository.findLatestLocations(pageable);

		trajectoriesPage.getContent().forEach(System.out::println);

		// Assert
		assertThat(trajectoriesPage).isNotNull();
		assertThat(trajectoriesPage.getTotalElements()).as("Total elements should be 2").isEqualTo(2);
		assertThat(formatStringToDate.format(trajectoriesPage.getContent().get(0).getDate()))
				.as("First element should have date 01-01-2024 10:30:15")
				.isEqualTo("01-01-2024 10:30:15");
		assertThat(formatStringToDate.format(trajectoriesPage.getContent().get(1).getDate()))
				.as("Second element should have date 28-02-2024 08:00:05")
				.isEqualTo("28-02-2024 08:00:05");
	}
}
