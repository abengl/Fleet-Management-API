package com.fleetmanagement.api_rest.repository;

import com.fleetmanagement.api_rest.model.Taxi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class TaxiRepositoryTest {

	@Autowired
	private TaxiRepository taxiRepository;

	@Autowired
	private TestEntityManager entityManager;

	@BeforeEach
	void setUp() {
		Taxi taxi = new Taxi(1, "ABC-123");
		entityManager.persist(taxi);
	}

	@Test
	@DisplayName("Positive test - Find by ID and Plate")
	void findByIdOrPlateTest() {
		// Given
		Integer id = 1;
		String plate = "ABC-123";
		Pageable pageable = PageRequest.of(0, 5);

		// When
		Page<Taxi> found = taxiRepository.findByIdOrPlate(id, plate, pageable);
		System.out.println(found);
		// Then
		assertThat(found.getContent().get(0).getId()).isEqualTo(id);
		assertThat(found.getContent().get(0).getPlate()).isEqualTo(plate);
	}
}
