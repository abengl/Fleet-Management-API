package com.fleetmanagement.api_rest.persistence.entity;

import com.fleetmanagement.api_rest.persistence.entity.Taxi;
import com.fleetmanagement.api_rest.persistence.entity.Trajectory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
class TaxiTest {

	private Taxi taxi;

	@BeforeEach
	void setUp() {
		List<Trajectory> trajectories = new ArrayList<>();
		taxi = new Taxi(123, "ABC-123", trajectories);
	}

	@Test
	void shouldCreateTaxiWithAttributes() {
		assertEquals(123, taxi.getId(), "Taxi id was not equal to 123.");
		assertEquals("ABC-123", taxi.getPlate(), "Taxi plate was notequal to ABC-123.");
		assertTrue(taxi.getTrajectories().isEmpty(), "Taxi trajectories list was not empty.");
	}

}