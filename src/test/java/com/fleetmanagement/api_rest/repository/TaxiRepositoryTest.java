package com.fleetmanagement.api_rest.repository;

import com.fleetmanagement.api_rest.model.Taxi;
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

import java.util.ArrayList;

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
		taxiRepository.save(new Taxi(1,"ABC-123", new ArrayList<>()));
		taxiRepository.save(new Taxi(2,"abc-456", new ArrayList<>()));
		taxiRepository.save(new Taxi(3,"PQR-000", new ArrayList<>()));
	}

	@Test
	@DisplayName("TaxiRepository - Testing method FindAll() - It should return a taxi page with pagination by default" +
			" " +
			"starts at page 0 with a limit of 10 items")
	public void TaxiRepository_findAll_ReturnTaxiPage(){
		// Act
		Pageable pageable = PageRequest.of(0, 5);
		Page<Taxi> taxiPage = taxiRepository.findAll(pageable);

		// Debug output
		System.out.println("Total elements: " + taxiPage.getTotalElements());
		System.out.println("Total pages: " + taxiPage.getTotalPages());
		System.out.println("Current page number: " + taxiPage.getNumber());
		System.out.println("Page content:");
		taxiPage.getContent().forEach(taxi ->
				System.out.println("Taxi ID: " + taxi.getId() + ", Plate: " + taxi.getPlate())
		);

		// Assert
		assertThat(taxiPage).isNotNull();
		assertThat(taxiPage.getContent()).hasSize(3);
		assertThat(taxiPage.getContent().get(0).getPlate()).isIn("ABC-123");
	}

	@Test
	@DisplayName("TaxiRepository - Testing existsById() - It should return true if an id exists in the db or false " +
			"otherwise")
	public void TaxiRepository_existsById_ReturnBoolean() {
		// Act
		boolean case1 = taxiRepository.existsById(1);
		boolean case2 = taxiRepository.existsById(100);
		// Assert
		assertThat(case1).isTrue();
		assertThat(case2).isFalse();
	}

	@Test
	@DisplayName("TaxiRepository - Testing findByPlateContainingIgnoreCase() - It should return taxis with matching " +
			"plate")
	public void TaxiRepository_findByPlateContainingIgnoreCase_ReturnTaxiPage() {
		// Act
		Pageable pageable = PageRequest.of(0, 3);
		Page<Taxi> taxiPage = taxiRepository.findByPlateContainingIgnoreCase("abc", pageable);

		// Assert
		assertThat(taxiPage).isNotNull();
		assertThat(taxiPage.getContent()).hasSize(2); // "ABC-123" and "abc-456"
		assertThat(taxiPage.getContent()).extracting(Taxi::getPlate)
				.containsExactlyInAnyOrder("ABC-123", "abc-456");
	}

}
