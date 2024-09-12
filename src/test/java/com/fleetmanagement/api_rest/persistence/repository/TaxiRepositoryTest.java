package com.fleetmanagement.api_rest.persistence.repository;


import com.fleetmanagement.api_rest.persistence.entity.Taxi;
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

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaxiRepositoryTest {

	@Autowired
	private TaxiRepository taxiRepository;

	@BeforeEach
	public void setUp() {
		// Arrange
		List<Taxi> taxis = Arrays.asList(
				new Taxi(1, "ABC-123"),
				new Taxi(2, "abc-456"),
				new Taxi(3, "PQR-000"),
				new Taxi(4, "pbc-963"),
				new Taxi(5, "FGH-789"),
				new Taxi(6, "FGH-456"),
				new Taxi(7, "FGH-951")
		);
		taxiRepository.saveAll(taxis);
	}

	@Test
	@DisplayName("Testing method findAll() - It should return a page with all taxis with pagination")
	public void findAllTest() {
		// Act
		Pageable pageable = PageRequest.of(0, 5);
		Page<Taxi> taxiPage = taxiRepository.findAll(pageable);

		// Assert
		assertThat(taxiPage).isNotNull();
		assertThat(taxiPage.getTotalPages()).as("Total pages should be 2").isEqualTo(2);
		assertThat(taxiPage.getTotalElements()).as("Total elements should be 7").isEqualTo(7);
		assertThat(taxiPage.getContent().get(0).getPlate()).as("The first plate should be ABC-123")
				.isEqualTo("ABC-123");
	}

	@Test
	@DisplayName("Testing findByPlateContainingIgnoreCase() - It should return a taxi page matching the plate")
	public void findByPlateTest() {
		// Act
		Pageable pageable = PageRequest.of(0, 5);
		Page<Taxi> taxiPage = taxiRepository.findByPlateContainingIgnoreCase("bc", pageable);

		// Assert
		assertThat(taxiPage).isNotNull();
		assertThat(taxiPage.getContent()).as("Page should have 3 objects").hasSize(3);
		assertThat(taxiPage.getContent()).extracting(Taxi::getPlate)
				.containsExactlyInAnyOrder("ABC-123", "abc-456", "pbc-963");
	}

}

