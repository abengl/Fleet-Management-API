package com.fleetmanagement.api_rest.presentation.controller;

import com.fleetmanagement.api_rest.business.exception.ValueNotFoundException;
import com.fleetmanagement.api_rest.business.service.TrajectoryService;
import com.fleetmanagement.api_rest.presentation.dto.LatestTrajectoryDTO;
import com.fleetmanagement.api_rest.presentation.dto.TrajectoryDTO;
import com.fleetmanagement.api_rest.presentation.dto.TrajectoryExportResponse;
import com.fleetmanagement.api_rest.utils.ExcelExporter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class TrajectoryController {

	private final TrajectoryService trajectoryService;

	/**
	 * Exports all trajectories to an Excel file based on the provided taxi ID and date.
	 * <p>
	 * This method sets the response content type to "application/octet-stream" and
	 * sets the Content-Disposition header to prompt a file download with a filename
	 * that includes the taxi ID and date. It retrieves the trajectories from the
	 * service layer and uses the ExcelExporter utility to generate the Excel file.
	 * If no trajectories are found, a ValueNotFoundException is thrown.
	 *
	 * @param taxiId   the ID of the taxi (optional)
	 * @param date     the date of the trajectories (optional)
	 * @param response the HttpServletResponse to write the Excel file to
	 */
	@GetMapping("/export")
	public void getAllTrajectoriesToExport(
			@RequestParam(name = "taxiId", required = false) Integer taxiId,
			@RequestParam(name = "date", required = false) String date, HttpServletResponse response) {

		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=trajectories_" + taxiId + "_" + date + ".xls";
		response.setHeader(headerKey, headerValue);

		List<TrajectoryExportResponse> trajectories = trajectoryService.getExportData(taxiId, date);

		if (trajectories == null || trajectories.isEmpty()) {
			throw new ValueNotFoundException("No trajectories found for the given taxi ID and date.");
		}
		System.out.println("Exporting " + trajectories.size() + " trajectories.");

		ExcelExporter excelExporter = new ExcelExporter(trajectories);
		excelExporter.generateExcelFile(response);
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
