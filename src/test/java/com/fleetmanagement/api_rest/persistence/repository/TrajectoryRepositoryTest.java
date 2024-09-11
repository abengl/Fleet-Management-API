package com.fleetmanagement.api_rest.persistence.repository;

import com.fleetmanagement.api_rest.persistence.entity.Taxi;
import com.fleetmanagement.api_rest.persistence.entity.Trajectory;
import com.fleetmanagement.api_rest.persistence.repository.TaxiRepository;
import com.fleetmanagement.api_rest.persistence.repository.TrajectoryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
import java.util.ArrayList;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TrajectoryRepositoryTest {

	@Autowired
	TrajectoryRepository trajectoryRepository;

	@Autowired
	TaxiRepository taxiRepository;

	/*@BeforeAll
	void setUp() throws ParseException {
		// Arrange
		Taxi taxi1 = new Taxi(1, "ABC-123", null);
		Taxi taxi2 = new Taxi(2, "abc-456", null);

		taxiRepository.save(taxi1);
		taxiRepository.save(taxi2);

		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse("2024-01-01");
		Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse("2024-02-02");
		Date date3 = new SimpleDateFormat("yyyy-MM-dd").parse("2024-01-31");
		Date date4 = new SimpleDateFormat("yyyy-MM-dd").parse("2024-02-28");

		Trajectory trajectory1 = new Trajectory(10, taxi1, date1, 100.00, -100.00);
		Trajectory trajectory2 = new Trajectory(20, taxi2, date2, 200.00, -200.00);
		Trajectory trajectory3 = new Trajectory(30, taxi1, date3, 100.00, -100.00);
		Trajectory trajectory4 = new Trajectory(40, taxi2, date4, 200.00, -200.00);

		trajectoryRepository.save(trajectory1);
		trajectoryRepository.save(trajectory2);
		trajectoryRepository.save(trajectory3);
		trajectoryRepository.save(trajectory4);
	}*/

	@BeforeAll
	void setUp() throws ParseException {
		// Arrange
		Taxi taxi1 = new Taxi(1, "ABC-123", new ArrayList<>());
		Taxi taxi2 = new Taxi(2, "abc-456", new ArrayList<>());

		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse("2024-01-01");
		Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse("2024-02-02");
		Date date3 = new SimpleDateFormat("yyyy-MM-dd").parse("2024-01-31");
		Date date4 = new SimpleDateFormat("yyyy-MM-dd").parse("2024-02-28");

		Trajectory trajectory1 = new Trajectory(10, taxi1, date1, 100.00, -100.00);
		Trajectory trajectory2 = new Trajectory(20, taxi2, date2, 200.00, -200.00);
		Trajectory trajectory3 = new Trajectory(30, taxi1, date3, 100.00, -100.00);
		Trajectory trajectory4 = new Trajectory(40, taxi2, date4, 200.00, -200.00);

		// Add the trajectories to their respective taxis
		taxi1.getTrajectories().add(trajectory1);
		taxi1.getTrajectories().add(trajectory3);
		taxi2.getTrajectories().add(trajectory2);
		taxi2.getTrajectories().add(trajectory4);

		// Save the taxis, which will also cascade save the trajectories
		taxiRepository.save(taxi1);
		taxiRepository.save(taxi2);
	}


	@SuppressWarnings("SequencedCollectionMethodCanBeUsed")
	@Test
	@DisplayName("Testing method findByTaxiId_IdAndDate() - it should return a page with the matching trajectories")
	public void findByTaxiIdAndDateTest() throws ParseException {

		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse("2024-01-01");
		Pageable pageable = PageRequest.of(0, 4);

		// Act
		Page<Trajectory> trajectoriesPage = trajectoryRepository.findByTaxiId_IdAndDate(1, date1, pageable);

		// Debug output
		System.out.println("Page content:");
		trajectoriesPage.getContent().forEach(trajectory -> System.out.println(
				"Trajectory ID: " + trajectory.getId() + "\n" + "TaxiID: " + trajectory.getTaxiId()
						.getId() + "\n" + "Date: " + trajectory.getDate() + "\n" + "Latitude: " + trajectory.getLatitude() + "\n" + "Longitude: " + trajectory.getLongitude()));

		// Assert
		assertThat(trajectoriesPage).isNotNull();
		assertThat(trajectoriesPage.getTotalElements()).isEqualTo(1);
		assertThat(trajectoriesPage.getContent().get(0).getId()).isEqualTo(10);
	}

	@Test
	@DisplayName("Testing method findAll() - it should return a page with all trajectories")
	public void findAllTest() {

		Pageable pageable = PageRequest.of(0, 4);

		// Act
		Page<Trajectory> trajectoriesPage = trajectoryRepository.findAll(pageable);

		// Debug output
		System.out.println("Page content:");
		trajectoriesPage.getContent().forEach(trajectory -> System.out.println(
				"Trajectory ID: " + trajectory.getId() + "\n" + "TaxiID: " + trajectory.getTaxiId()
						.getId() + "\n" + "Date: " + trajectory.getDate() + "\n" + "Latitude: " + trajectory.getLatitude() + "\n" + "Longitude: " + trajectory.getLongitude()));

		// Assert
		assertThat(trajectoriesPage).isNotNull();
		assertThat(trajectoriesPage.getTotalElements()).isEqualTo(4);
		assertThat(trajectoriesPage.getContent().get(1).getId()).isEqualTo(20);
	}

	@Test
	@DisplayName("Testing method findLatestLocations() - it should return a page with the latest " + "trajectories")
	public void findLatestLocationsTest() {

		Pageable pageable = PageRequest.of(0, 4);

		// Act
		Page<Trajectory> trajectoriesPage = trajectoryRepository.findLatestLocations(pageable);

		// Debug output
		System.out.println("Page content:");
		trajectoriesPage.getContent().forEach(trajectory -> System.out.println(
				"Trajectory ID: " + trajectory.getId() + "\n" + "TaxiID: " + trajectory.getTaxiId()
						.getId() + "\n" + "Date: " + trajectory.getDate() + "\n" + "Latitude: " + trajectory.getLatitude() + "\n" + "Longitude: " + trajectory.getLongitude()));

		// Assert
		assertThat(trajectoriesPage).isNotNull();
		assertThat(trajectoriesPage.getTotalElements()).isEqualTo(2);
		assertThat(trajectoriesPage.getContent().get(0).getId()).isEqualTo(30);
		assertThat(trajectoriesPage.getContent().get(1).getId()).isEqualTo(40);
	}
}