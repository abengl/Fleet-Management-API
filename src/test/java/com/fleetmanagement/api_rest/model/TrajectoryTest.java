package com.fleetmanagement.api_rest.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // initialize mock objects in JUnit tests with Mockito
@ActiveProfiles("test")
class TrajectoryTest {

	@Mock //mock out certain dependencies without initializing the Spring context.
	private Taxi taxi;

	@Test
	@DisplayName("Trajectory - Testing constructor - It should return a Trajectory object")
	public void Trajectory_constructor_ReturnObject() {
		// Arrange
		when(taxi.getId()).thenReturn(1);

		Date date = new Date();

		// Act
		Trajectory trajectory = new Trajectory(100, taxi, date, 40.753, -90.852);

		// Print debugging information
		System.out.println("Taxi ID from mock: " + taxi.getId());
		System.out.println("Taxi ID from Trajectory: " + trajectory.getTaxiId().getId());
		System.out.println("Latitude: " + trajectory.getLatitude());
		System.out.println("Longitude: " + trajectory.getLongitude());
		System.out.println("Date: " + trajectory.getDate());

		// Assert
		assertNotNull(trajectory);
		assertEquals(100, trajectory.getId());
		assertEquals(taxi.getId(), trajectory.getTaxiId().getId());
		assertEquals(date, trajectory.getDate());
		assertEquals(40.753, trajectory.getLatitude());
		assertEquals(-90.852, trajectory.getLongitude());

	}
}