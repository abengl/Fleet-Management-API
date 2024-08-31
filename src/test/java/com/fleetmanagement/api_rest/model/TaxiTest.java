package com.fleetmanagement.api_rest.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Taxi model Unit Test with JUnit5")
public class TaxiTest {

	@Test
	@DisplayName("1-Testing the class model instantiation")
	public void testTaxiModel() {
		// Set up the environment and assumptions
		// Run the app
		// Verify results
		Taxi taxi = new Taxi(1, "ABC-123");
		assertEquals(1, taxi.getId());
		assertEquals("ABC-123", taxi.getPlate());
	}
	@Test
	@DisplayName("2-Testing the class model modification")
	public void testTaxiModelSet() {
		// Set up the environment and assumptions
		// Run the app
		// Verify results
		Taxi taxi = new Taxi();
		taxi.setId(2);
		taxi.setPlate("DEF-951");
		assertEquals(2, taxi.getId());
		assertEquals("DEF-951", taxi.getPlate());
	}
}