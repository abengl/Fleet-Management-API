package com.fleetmanagement.api_rest.utils.mapper;

import com.fleetmanagement.api_rest.persistence.entity.Taxi;
import com.fleetmanagement.api_rest.presentation.dto.TaxiDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaxiMapper {
	TaxiDTO toTaxiDTO(Taxi taxi); // Method to map Taxi to TaxiDTO
}

