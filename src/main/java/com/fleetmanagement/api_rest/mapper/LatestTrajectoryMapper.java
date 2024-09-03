package com.fleetmanagement.api_rest.mapper;

import com.fleetmanagement.api_rest.dto.LatestTrajectoryDTO;
import com.fleetmanagement.api_rest.model.Trajectory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LatestTrajectoryMapper {
	@Mapping(source = "taxiId.id", target = "taxiId")
	@Mapping(source = "taxiId.plate", target = "plate")
	@Mapping(source = "date", target = "date", dateFormat = "yyyy-MM-dd HH:mm:ss")
	LatestTrajectoryDTO toLatestTrajectoryDTO(Trajectory trajectory);
}
