package com.fleetmanagement.api_rest.utils.mapper;

import com.fleetmanagement.api_rest.persistence.entity.TrajectoryEntity;
import com.fleetmanagement.api_rest.presentation.dto.LatestTrajectoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for converting between TrajectoryEntity and LatestTrajectoryDTO.
 * Utilizes MapStruct for automatic mapping.
 */
@Mapper(componentModel = "spring")
public interface LatestTrajectoryMapper {

	/**
	 * Converts a TrajectoryEntity to a LatestTrajectoryDTO.
	 *
	 * @param trajectoryEntity the TrajectoryEntity to convert
	 * @return the converted LatestTrajectoryDTO
	 */
	@Mapping(source = "taxiId.id", target = "taxiId")
	@Mapping(source = "taxiId.plate", target = "plate")
	@Mapping(source = "date", target = "date", dateFormat = "yyyy-MM-dd HH:mm:ss")
	LatestTrajectoryDTO toLatestTrajectoryDTO(TrajectoryEntity trajectoryEntity);

}
