package com.fleetmanagement.api_rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrajectoryDTO {
	private Integer id;
	private String plate;
	private Integer taxiId;
	private String date;
	private double latitude;
	private double longitude;

	public TrajectoryDTO() {
	}
}
