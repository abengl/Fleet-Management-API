package com.fleetmanagement.api_rest.presentation.controller;

import com.fleetmanagement.api_rest.presentation.controller.TaxiController;
import com.fleetmanagement.api_rest.presentation.dto.TaxiDTO;
import com.fleetmanagement.api_rest.business.exception.ValueNotFoundException;
import com.fleetmanagement.api_rest.business.service.TaxiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaxiController.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TaxiControllerTest {
	@MockBean
	//Mock de una capa, no necesita de un db en sí, métodos por defecto del repositorio
	// to mock a bean within the Spring context, often in integration or controller tests
	private TaxiService taxiService; //@Mock para clases regulares que no tienen anotaciones, se debe instanciar

	@Autowired //  inject dependencies managed by the Spring container
	private MockMvc mockMvc;

	@Test
	@DisplayName("TaxiController - Testing method getAllTaxisByPlate() - It should return a Taxi list at the endpoint" + " taxis that matches the plate parameter")
	public void TaxiService_getTaxis_ReturnListTaxis() throws Exception {
		// Arrange
		String plate = "ABC-123";
		int page = 0;
		int limit = 10;
		List<TaxiDTO> taxiList = Arrays.asList(new TaxiDTO(1, "ABC-123"), new TaxiDTO(2, "DEF-456"));

		when(taxiService.getTaxis(plate, page, limit)).thenReturn(taxiList);

		// Act-Assert
		mockMvc.perform(get("/taxis").param("plate", plate).param("page", String.valueOf(page))
						.param("limit", String.valueOf(limit)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(taxiList.size()))
				.andExpect(jsonPath("$[0].plate").value(taxiList.get(0).getPlate()))
				.andExpect(jsonPath("$[1].plate").value("DEF-456"));

		verify(taxiService).getTaxis(plate, page, limit);
	}

	@Test
	@DisplayName("TaxiController - Testing method getAllTaxisByPlate() - It should return a Taxi list at the endpoint" + " taxis with no plate parameter")
	public void testGetAllTaxisByPlate_DefaultPagination() throws Exception {
		// Arrange
		List<TaxiDTO> taxiList = Arrays.asList(new TaxiDTO(1, "ABC-123"), new TaxiDTO(2, "DEF-456"));

		when(taxiService.getTaxis(null, 0, 10)).thenReturn(taxiList);

		// Act-Assert
		mockMvc.perform(get("/taxis").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(taxiList.size()));

		verify(taxiService).getTaxis(null, 0, 10);
	}

	@Test
	@DisplayName("TaxiController - Testing method getAllTaxisByPlate() - It should return an exception not found when" + " the plate does not exist")
	public void testGetAllTaxisByPlate_EmptyResult() throws Exception {
		// Arrange
		String plate = "0000000";
		int page = 0;
		int limit = 10;

		when(taxiService.getTaxis(plate, page, limit)).thenThrow(
				new ValueNotFoundException("No taxis found with plate containing: " + plate));

		// Act & Assert
		mockMvc.perform(get("/taxis").param("plate", plate).param("page", String.valueOf(page))
						.param("limit", String.valueOf(limit)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.error").value("Value not found. No taxis found with plate containing: " + plate));


		verify(taxiService).getTaxis(plate, page, limit);
	}
}