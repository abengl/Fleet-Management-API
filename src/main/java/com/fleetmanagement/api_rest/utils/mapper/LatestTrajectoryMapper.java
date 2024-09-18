package com.fleetmanagement.api_rest.utils.mapper;

import com.fleetmanagement.api_rest.persistence.entity.Trajectory;
import com.fleetmanagement.api_rest.presentation.dto.LatestTrajectoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LatestTrajectoryMapper {
	@Mapping(source = "taxiId.id", target = "taxiId")
	@Mapping(source = "taxiId.plate", target = "plate")
	@Mapping(source = "date", target = "date", dateFormat = "yyyy-MM-dd HH:mm:ss")
	LatestTrajectoryDTO toLatestTrajectoryDTO(Trajectory trajectory);

}
