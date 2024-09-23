package com.fleetmanagement.api_rest.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing a trajectory.
 * This class contains information about the trajectory's ID, plate number, taxi ID, date, and its coordinates.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrajectoryDTO {
	private Integer id;
	private String plate;
	private Integer taxiId;
	private String date;
	private double latitude;
	private double longitude;
}
