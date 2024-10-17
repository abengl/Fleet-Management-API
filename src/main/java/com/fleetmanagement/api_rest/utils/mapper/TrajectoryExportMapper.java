package com.fleetmanagement.api_rest.utils.mapper;

import com.fleetmanagement.api_rest.persistence.entity.TrajectoryEntity;
import com.fleetmanagement.api_rest.presentation.dto.TrajectoryExportResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TrajectoryExportMapper {
	@Mapping(source = "taxiId.id", target = "taxiId")
	@Mapping(source = "taxiId.plate", target = "plate")
	@Mapping(source = "date", target = "date", dateFormat = "yyyy-MM-dd HH:mm:ss")
	@Mapping(source = "latitude", target = "latitude")
	@Mapping(source = "longitude", target = "longitude")
	TrajectoryExportResponse toTrajectoryExportResponse(TrajectoryEntity trajectoryEntity);
}
