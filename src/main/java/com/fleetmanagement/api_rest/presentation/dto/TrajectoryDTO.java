package com.fleetmanagement.api_rest.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
