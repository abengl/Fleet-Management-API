package com.fleetmanagement.api_rest.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing a taxi.
 * This class contains the taxi's ID and plate number.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaxiDTO {
	private Integer id;
	private String plate;
}