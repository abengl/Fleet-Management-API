package com.fleetmanagement.api_rest.dto;

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
}
