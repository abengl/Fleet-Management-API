package com.fleetmanagement.api_rest.mapper;

import com.fleetmanagement.api_rest.dto.TaxiDTO;
import com.fleetmanagement.api_rest.model.Taxi;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaxiMapper {
	TaxiDTO toTaxiDTO(Taxi taxi); // Method to map Taxi to TaxiDTO
}

