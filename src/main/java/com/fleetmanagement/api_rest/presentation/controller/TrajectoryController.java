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

/**
 * Controller for handling trajectory-related requests.
 */
@RestController
@RequestMapping("/trajectories")
public class TrajectoryController {

	private final TrajectoryService trajectoryService;

	@Autowired
	public TrajectoryController(TrajectoryService trajectoryService) {
		this.trajectoryService = trajectoryService;
	}

	/**
	 * Retrieves a list of trajectories based on the provided taxi ID, date, page, and limit.
	 *
	 * @param taxiId the ID of the taxi (optional)
	 * @param date   the date of the trajectory (optional)
	 * @param page   the page number for pagination (default is 0)
	 * @param limit  the maximum number of results per page (default is 20)
	 * @return a ResponseEntity containing a list of TrajectoryDTO objects
	 */
	@GetMapping
	public ResponseEntity<List<TrajectoryDTO>> getAllTrajectoriesByIdAndPlate(
			@RequestParam(name = "taxiId", required = false) Integer taxiId,
			@RequestParam(name = "date", required = false) String date,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "limit", defaultValue = "20") int limit) {

		List<TrajectoryDTO> trajectories = trajectoryService.getTrajectories(taxiId, date, page, limit);
		return ResponseEntity.ok(trajectories);

	}

	/**
	 * Retrieves the latest trajectories based on the provided page and limit.
	 *
	 * @param page  the page number for pagination (default is 0)
	 * @param limit the maximum number of results per page (default is 20)
	 * @return a ResponseEntity containing a list of LatestTrajectoryDTO objects
	 */
	@GetMapping("/latest")
	public ResponseEntity<List<LatestTrajectoryDTO>> getLatestTrajectories(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "limit", defaultValue = "20") int limit) {

		List<LatestTrajectoryDTO> latestTrajectories = trajectoryService.getLatestTrajectories(page, limit);
		return ResponseEntity.ok(latestTrajectories);

	}
}
