package com.fleetmanagement.api_rest.presentation.controller;

import com.fleetmanagement.api_rest.business.exception.ValueNotFoundException;
import com.fleetmanagement.api_rest.business.service.TaxiService;
import com.fleetmanagement.api_rest.presentation.dto.TaxiDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaxiController.class)
class TaxiControllerTest {
	int page = 0;

	@Autowired //  inject dependencies managed by the Spring container
	private MockMvc mockMvc;
	int limit = 10;
	List<TaxiDTO> taxiDTOList;
	@MockBean
	private TaxiService taxiService;

	@BeforeEach
	public void setUp() {
		taxiDTOList = Arrays.asList(
				TaxiDTO.builder().plate("ABC-123").build(),
				TaxiDTO.builder().plate("DEF-456").build());
	}

	@Test
	@DisplayName("Testing method getAllTaxisByPlate() - It should return a Taxi list that matches the plate parameter")
	public void getTaxisTest() throws Exception {
		String plate = "ABC-123";

		when(taxiService.getTaxis(plate, page, limit)).thenReturn(taxiDTOList);

		System.out.println("T1 - Taxis:");
		taxiDTOList.forEach(System.out::println);

		mockMvc.perform(get("/taxis")
						.param("plate", plate)
						.param("page", String.valueOf(page))
						.param("limit", String.valueOf(limit)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(taxiDTOList.size()))
				.andExpect(jsonPath("$[0].plate").value(taxiDTOList.get(0).getPlate()))
				.andExpect(jsonPath("$[1].plate").value(taxiDTOList.get(1).getPlate()));

		verify(taxiService).getTaxis(plate, page, limit);
	}

	@Test
	@DisplayName("Testing method getAllTaxisByPlate() - It should return a Taxi list")
	public void testGetTaxisByPlateTest() throws Exception {
		when(taxiService.getTaxis(null, page, limit)).thenReturn(taxiDTOList);

		System.out.println("T2 - Taxis:");
		taxiDTOList.forEach(System.out::println);

		mockMvc.perform(get("/taxis")
						.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(taxiDTOList.size()));

		verify(taxiService).getTaxis(null, page, limit);
	}

	@Test
	@DisplayName("Testing method getAllTaxisByPlate() - It should return an exception not found when" + " the plate " +
			"does not exist")
	public void getTaxisExceptionTest() throws Exception {
		String plate = "0000000";

		when(taxiService.getTaxis(plate, page, limit)).thenThrow(
				new ValueNotFoundException("No taxis found with plate containing: " + plate));

		mockMvc.perform(get("/taxis")
						.param("plate", plate).param("page", String.valueOf(page))
						.param("limit", String.valueOf(limit)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(
						jsonPath("$.error").value("Value not found. No taxis found with plate containing: " + plate));

		verify(taxiService).getTaxis(plate, page, limit);
	}
}