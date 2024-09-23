package com.fleetmanagement.api_rest.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing the latest trajectory of a taxi.
 * This class contains information about the taxi's ID, plate number, date, and its last known coordinates.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LatestTrajectoryDTO {
	private Integer taxiId;
	private String plate;
	private String date;
	private double latitude;
	private double longitude;
}
