package com.fleetmanagement.api_rest.utils.mapper;

import com.fleetmanagement.api_rest.persistence.entity.TrajectoryEntity;
import com.fleetmanagement.api_rest.presentation.dto.TrajectoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for converting between TrajectoryEntity and TrajectoryDTO.
 * Utilizes MapStruct for automatic mapping.
 */
@Mapper(componentModel = "spring")
public interface TrajectoryMapper {

	/**
	 * Converts a TrajectoryEntity to a TrajectoryDTO.
	 *
	 * @param trajectoryEntity the TrajectoryEntity to convert
	 * @return the converted TrajectoryDTO
	 */
	@Mapping(source = "taxiId.id", target = "taxiId")
	@Mapping(source = "taxiId.plate", target = "plate")
	@Mapping(source = "date", target = "date", dateFormat = "yyyy-MM-dd HH:mm:ss")
	TrajectoryDTO toTrajectoryDTO(TrajectoryEntity trajectoryEntity);

}
