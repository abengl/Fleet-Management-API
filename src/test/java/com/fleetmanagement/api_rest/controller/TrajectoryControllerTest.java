package com.fleetmanagement.api_rest.controller;

import com.fleetmanagement.api_rest.dto.LatestTrajectoryDTO;
import com.fleetmanagement.api_rest.dto.TrajectoryDTO;
import com.fleetmanagement.api_rest.model.Trajectory;
import com.fleetmanagement.api_rest.service.TrajectoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrajectoryController.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TrajectoryControllerTest {

	@MockBean
	TrajectoryService trajectoryService;
	@Autowired
	MockMvc mockMvc;

	@Test
	@DisplayName("Testing getAllTrajectoriesByIdAndPlate() - Should return a list of TrajectoryDTOs when valid")
	void getAllTrajectoriesByIdAndPlateTest() throws Exception {
		// Arrange
		List<TrajectoryDTO> trajectories = List.of(
				new TrajectoryDTO(1, "ABC-123", 1, "2024-01-01", 5.0, 6.0)
		);

		when(trajectoryService.getTrajectories(1, "01-01-2024", 0, 10)).thenReturn(trajectories);

		// Act & Assess
		mockMvc.perform(get("/trajectories")
						.param("taxiId", "1")
						.param("date", "01-01-2024")
						.param("page", "0")
						.param("limit", "10"))
				.andDo(print())  // Print the response to the console for debugging
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].plate").value("ABC-123"))
				.andExpect(jsonPath("$[0].taxiId").value(1))
				.andExpect(jsonPath("$[0].date").value("2024-01-01"))
				.andExpect(jsonPath("$[0].latitude").value(5.0))
				.andExpect(jsonPath("$[0].longitude").value(6.0));
	}

	@Test
	@DisplayName("Testing getLatestTrajectories() - It should return a response with a list of latest TrajectoryDTO")
	void getLatestTrajectoriesTest() throws Exception {
		// Arrange
		List<LatestTrajectoryDTO> latestTrajectories = List.of(
				new LatestTrajectoryDTO(1, "ABC-123","2024-01-01", 5.0, 6.0)
		);

		when(trajectoryService.getLatestTrajectories(0, 10)).thenReturn(latestTrajectories);

		// Act & Assess
		mockMvc.perform(get("/trajectories/latest")
						.param("page", "0")
						.param("limit", "10"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].plate").value("ABC-123"))
				.andExpect(jsonPath("$[0].taxiId").value(1))
				.andExpect(jsonPath("$[0].date").value("2024-01-01"))
				.andExpect(jsonPath("$[0].latitude").value(5.0))
				.andExpect(jsonPath("$[0].longitude").value(6.0));
	}
}