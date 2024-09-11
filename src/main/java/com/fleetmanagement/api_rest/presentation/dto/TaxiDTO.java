package com.fleetmanagement.api_rest.presentation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaxiDTO { // It has only the fields needed for the API response for simplicity.
	private Integer id;
	private String plate;

	public TaxiDTO() {
	}

	public TaxiDTO(Integer id, String plate) {
		this.id = id;
		this.plate = plate;
	}
}
