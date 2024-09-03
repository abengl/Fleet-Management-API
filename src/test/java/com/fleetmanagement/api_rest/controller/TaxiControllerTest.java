package com.fleetmanagement.api_rest.controller;

import com.fleetmanagement.api_rest.dto.TaxiDTO;
import com.fleetmanagement.api_rest.service.TaxiService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaxiController.class)
class TaxiControllerTest {

	@MockBean
	TaxiService taxiService;
	@Autowired
	MockMvc mockMvc;

	@Test
	public void getAllTaxisByPlateTest() throws Exception {
		List<TaxiDTO> taxiDTOList = new ArrayList<>();
		TaxiDTO taxiDTO = new TaxiDTO(123, "ABC-123");
		taxiDTOList.add(taxiDTO);

		Mockito.when(taxiService.getTaxis(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
				.thenAnswer(invocation -> {
					System.out.println(
							"Mocked service call with arguments: " + Arrays.toString(invocation.getArguments()));
					return taxiDTOList;
				});

		mockMvc.perform(get("/taxis"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].id").value(123))
				.andExpect(jsonPath("$[0].plate").value("ABC-123"))
				.andDo(result -> {
					System.out.println("Response: " + result.getResponse().getContentAsString());
				});
	}

}