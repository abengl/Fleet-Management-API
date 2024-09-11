package com.fleetmanagement.api_rest.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaxiDTO { // It has only the fields needed for the API response for simplicity.

	private Integer id;
	private String plate;

}
