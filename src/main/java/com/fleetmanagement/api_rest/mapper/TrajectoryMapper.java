package com.fleetmanagement.api_rest.mapper;

import com.fleetmanagement.api_rest.dto.TrajectoryDTO;
import com.fleetmanagement.api_rest.model.Trajectory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TrajectoryMapper {
	@Mapping(source = "taxiId.id", target = "taxiId")
	@Mapping(source = "taxiId.plate", target = "plate")
	@Mapping(source = "date", target = "date", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	TrajectoryDTO toTrajectoryDTO(Trajectory trajectory);

}
