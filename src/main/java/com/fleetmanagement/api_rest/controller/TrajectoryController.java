package com.fleetmanagement.api_rest.controller;

import com.fleetmanagement.api_rest.dto.TrajectoryDTO;
import com.fleetmanagement.api_rest.exception.InvalidFormatException;
import com.fleetmanagement.api_rest.service.TrajectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/trajectories")
public class TrajectoryController {

	private final TrajectoryService trajectoryService;

	@Autowired
	public TrajectoryController(TrajectoryService trajectoryService) {
		this.trajectoryService = trajectoryService;
	}

	@GetMapping
	public ResponseEntity<List<TrajectoryDTO>> getAllTrajectoriesByIdAndPlate(
			@RequestParam(name = "taxiId") Integer taxiId, @RequestParam(name = "date") String date,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "limit", defaultValue = "20") int limit) {
		try {
			List<TrajectoryDTO> trajectories = trajectoryService.getTrajectories(taxiId, date, page, limit);
			return ResponseEntity.ok(trajectories);
		} catch (ParseException e) {
			throw new InvalidFormatException("The date format should be dd-MM-yyyy");
		}
	}
}
