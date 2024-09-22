package com.fleetmanagement.api_rest.utils.mapper;

import com.fleetmanagement.api_rest.persistence.entity.TaxiEntity;
import com.fleetmanagement.api_rest.presentation.dto.TaxiDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaxiMapper {
	TaxiDTO toTaxiDTO(TaxiEntity taxiEntity); // Method to map Taxi to TaxiDTO
}

