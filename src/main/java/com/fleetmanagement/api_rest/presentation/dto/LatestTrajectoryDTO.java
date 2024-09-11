package com.fleetmanagement.api_rest.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LatestTrajectoryDTO {

	private Integer taxiId;
	private String plate;
	private String date;
	private double latitude;
	private double longitude;

}
