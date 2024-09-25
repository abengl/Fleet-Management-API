package com.fleetmanagement.api_rest.persistence.repository;

import com.fleetmanagement.api_rest.persistence.entity.TrajectoryEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TrajectoryRepositoryTest {

	@Autowired
	TrajectoryRepository trajectoryRepository;

	@Autowired
	TaxiRepository taxiRepository;

	private final Integer taxiId1 = 6418;

	@Test
	@DisplayName("Verify Trajectory Data")
	public void verifyTestData() {
		trajectoryRepository.findAll().forEach(System.out::println);
		assertThat(trajectoryRepository.findAll()).hasSize(4);
	}

	@Test
	@DisplayName("Testing method existsById() - It should return a boolean")
	public void existsByIdTest() {
		boolean expected1 = taxiRepository.existsById(taxiId1);
		boolean expected2 = taxiRepository.existsById(55555);

		assertThat(expected1).as("Taxi with ID " + taxiId1 + " should exist.").isTrue();
		assertThat(expected2).as("Taxi with ID 55555 should not exist.").isFalse();
	}

	@Test
	@DisplayName("Testing method findByTaxiId_IdAndDate() - it should return a page with the matching trajectories")
	public void findByTaxiIdAndDateTest() throws ParseException {

		SimpleDateFormat formatStringToDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		Date date = formatStringToDate.parse("02-02-2008");

		Pageable pageable = PageRequest.of(0, 4);

		// Act
		Page<TrajectoryEntity> trajectoriesPage = trajectoryRepository.findByTaxiId_IdAndDate(taxiId1, date, pageable);

		// Assert
		assertThat(trajectoriesPage).isNotNull();
		assertThat(trajectoriesPage.getTotalElements()).as("Total elements should be 2").isEqualTo(2);
		assertThat(trajectoriesPage.getContent().get(0).getTaxiId().getPlate()).as(
				"First element should have plate CNBC-2997").isEqualTo("CNBC-2997");
		assertThat(formatStringToDate.format(trajectoriesPage.getContent().get(1).getDate()))
				.as("Second element should have date 02-02-2008")
				.isEqualTo("02-02-2008");
	}

	@Test
	@DisplayName("Testing method findLatestLocations() - it should return a page with the latest trajectories")
	public void findLatestLocationsTest() {
		SimpleDateFormat formatStringToDate = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.ENGLISH);
		Pageable pageable = PageRequest.of(0, 4);

		// Act
		Page<TrajectoryEntity> trajectoriesPage = trajectoryRepository.findLatestLocations(pageable);

		// Assert
		assertThat(trajectoriesPage).isNotNull();
		assertThat(trajectoriesPage.getTotalElements()).as("Total elements should be 3").isEqualTo(3);
		assertThat(formatStringToDate.format(trajectoriesPage.getContent().get(0).getDate()))
				.as("First element should have date 02-02-2008 02:25:54")
				.isEqualTo("02-02-2008 02:25:54");
		assertThat(formatStringToDate.format(trajectoriesPage.getContent().get(1).getDate()))
				.as("Second element should have date 02-02-2008 01:40:26")
				.isEqualTo("02-02-2008 01:40:26");
		assertThat(formatStringToDate.format(trajectoriesPage.getContent().get(2).getDate()))
				.as("Second element should have date 02-02-2008 02:36:10")
				.isEqualTo("02-02-2008 02:36:10");
	}
}
