package com.fleetmanagement.api_rest.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
