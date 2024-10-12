package com.fleetmanagement.api_rest.presentation.controller;

import com.fleetmanagement.api_rest.business.exception.ValueNotFoundException;
import com.fleetmanagement.api_rest.business.service.EmailService;
import com.fleetmanagement.api_rest.business.service.TrajectoryService;
import com.fleetmanagement.api_rest.presentation.dto.LatestTrajectoryDTO;
import com.fleetmanagement.api_rest.presentation.dto.TrajectoryDTO;
import com.fleetmanagement.api_rest.presentation.dto.TrajectoryExportResponse;
import com.fleetmanagement.api_rest.utils.ExcelExporter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Controller for handling trajectory-related requests.
 */
@RestController
@RequestMapping("/trajectories")
@RequiredArgsConstructor
public class TrajectoryController {

	private final TrajectoryService trajectoryService;
	private final EmailService emailService;

	/**
	 * Sends an email with an attachment containing all trajectories based on the provided taxi ID and date.
	 * <p>
	 * This method retrieves the trajectories from the service layer and uses the ExcelExporter utility
	 * to generate an Excel file. The generated file is then sent as an email attachment.
	 * If no trajectories are found, a ValueNotFoundException is thrown.
	 *
	 * @param taxiId the ID of the taxi (optional)
	 * @param date   the date of the trajectories (optional)
	 * @param email  the recipient email address (required)
	 * @return a ResponseEntity indicating the result of the email sending operation
	 */

	@GetMapping("/sendmail")
	public ResponseEntity<?> getAllTrajectoriesToEmail(@RequestParam(name = "taxiId", required = false) Integer taxiId,
													   @RequestParam(name = "date", required = false) String date,
													   @RequestParam(name = "email", required = false) String email) {

		List<TrajectoryExportResponse> trajectories = trajectoryService.getExportData(taxiId, date);

		if (trajectories == null || trajectories.isEmpty()) {
			throw new ValueNotFoundException("No trajectories found for the given taxi ID and date.");
		}

		ExcelExporter exporter = new ExcelExporter(trajectories);
		ByteArrayOutputStream excelStream = exporter.generateExcelFileAsStream();

		try {
			emailService.sendWithAttachmentExcel(email, taxiId, date, excelStream);
			return ResponseEntity.ok("Email sent successfully!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Email failed to send: " + e.getMessage());
		}
	}

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
	public ResponseEntity<?> getAllTrajectoriesToExport(@RequestParam(name = "taxiId", required = false) Integer taxiId,
														@RequestParam(name = "date", required = false) String date,
														HttpServletResponse response) {

		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=trajectories_" + taxiId + "_" + date + ".xls";
		response.setHeader(headerKey, headerValue);

		List<TrajectoryExportResponse> trajectories = trajectoryService.getExportData(taxiId, date);

		if (trajectories == null || trajectories.isEmpty()) {
			throw new ValueNotFoundException("No trajectories found for the given taxi ID and date.");
		}
		System.out.println("Generating file of size: " + trajectories.size());

		ExcelExporter excelExporter = new ExcelExporter(trajectories);

		try {
			excelExporter.generateExcelFileAsXls(response);
			return ResponseEntity.ok("Excel file generated successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error while generating excel file: " + e.getMessage());
		}
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
