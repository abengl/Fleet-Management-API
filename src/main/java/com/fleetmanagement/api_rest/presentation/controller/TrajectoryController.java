package com.fleetmanagement.api_rest.presentation.controller;

import com.fleetmanagement.api_rest.business.service.TrajectoryService;
import com.fleetmanagement.api_rest.presentation.dto.LatestTrajectoryDTO;
import com.fleetmanagement.api_rest.presentation.dto.TrajectoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
			@RequestParam(name = "taxiId", required = false) Integer taxiId,
			@RequestParam(name = "date", required = false) String date,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "limit", defaultValue = "20") int limit) {

		List<TrajectoryDTO> trajectories = trajectoryService.getTrajectories(taxiId, date, page, limit);
		return ResponseEntity.ok(trajectories);

	}

	@GetMapping("/latest")
	public ResponseEntity<List<LatestTrajectoryDTO>> getLatestTrajectories(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "limit", defaultValue = "20") int limit) {

		List<LatestTrajectoryDTO> latestTrajectories = trajectoryService.getLatestTrajectories(page, limit);
		return ResponseEntity.ok(latestTrajectories);

	}
}
