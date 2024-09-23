package com.fleetmanagement.api_rest.utils.mapper;

import com.fleetmanagement.api_rest.persistence.entity.TaxiEntity;
import com.fleetmanagement.api_rest.presentation.dto.TaxiDTO;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between TaxiEntity and TaxiDTO.
 * Utilizes MapStruct for automatic mapping.
 */
@Mapper(componentModel = "spring")
public interface TaxiMapper {

	/**
	 * Converts a TaxiEntity to a TaxiDTO.
	 *
	 * @param taxiEntity the TaxiEntity to convert
	 * @return the converted TaxiDTO
	 */
	TaxiDTO toTaxiDTO(TaxiEntity taxiEntity);
}
