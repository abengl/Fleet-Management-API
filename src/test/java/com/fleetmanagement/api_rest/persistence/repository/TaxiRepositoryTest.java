package com.fleetmanagement.api_rest.persistence.repository;


import com.fleetmanagement.api_rest.persistence.entity.TaxiEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaxiRepositoryTest {

	@Autowired
	private TaxiRepository taxiRepository;

	@Test
	@DisplayName("Verify Taxi Data")
	public void verifyTestData() {
		List<TaxiEntity> taxis = taxiRepository.findAll();
		taxis.forEach(taxi -> System.out.println(taxi.getPlate()));
		assertThat(taxis).hasSize(3);
	}

	@Test
	@DisplayName("Testing method findAll() - It should return a page with all taxis with pagination")
	public void findAllTest() {
		// Act
		Pageable pageable = PageRequest.of(0, 3);
		Page<TaxiEntity> taxiPage = taxiRepository.findAll(pageable);
		taxiPage.getContent().forEach(System.out::println);

		// Assert
		assertThat(taxiPage).isNotNull();
		assertThat(taxiPage.getTotalPages()).as("Total pages should be 1").isEqualTo(1);
		assertThat(taxiPage.getTotalElements()).as("Total elements should be 3").isEqualTo(3);
		assertThat(taxiPage.getContent().get(0).getPlate()).as("The first plate should be CNBC-2997")
				.isEqualTo("CNBC-2997");
	}

	@Test
	@DisplayName("Testing findByPlateContainingIgnoreCase() - It should return a taxi page matching the plate")
	public void findByPlateTest() {
		// Act
		Pageable pageable = PageRequest.of(0, 2);
		Page<TaxiEntity> taxiPage = taxiRepository.findByPlateContainingIgnoreCase("bc", pageable);

		taxiPage.getContent().forEach(System.out::println);

		// Assert
		assertThat(taxiPage).isNotNull();
		assertThat(taxiPage.getContent()).as("Page should have 2 objects").hasSize(2);
		assertThat(taxiPage.getContent()).extracting(TaxiEntity::getPlate)
				.containsExactlyInAnyOrder("CNBC-2997", "BCMG-3071");
	}

}

