package com.fleetmanagement.api_rest.presentation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LatestTrajectoryDTO {
	private Integer taxiId;
	private String plate;
	private String date;
	private double latitude;
	private double longitude;

	public LatestTrajectoryDTO() {
	}

	public LatestTrajectoryDTO(Integer taxiId, String plate, String date, double latitude, double longitude) {
		this.taxiId = taxiId;
		this.plate = plate;
		this.date = date;
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
