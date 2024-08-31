package com.fleetmanagement.api_rest.mapper;

import com.fleetmanagement.api_rest.dto.TaxiDTO;
import com.fleetmanagement.api_rest.model.Taxi;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaxiMapper {
	TaxiMapper INSTANCE = Mappers.getMapper(TaxiMapper.class);

	TaxiDTO toTaxiDTO(Taxi taxi); // Method to map Taxi to TaxiDTO
	Taxi toTaxiEntity(TaxiDTO taxiDTO); // Method to map TaxiDTO to Taxi
}

