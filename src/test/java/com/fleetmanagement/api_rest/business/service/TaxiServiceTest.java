package com.fleetmanagement.api_rest.business.service;


import com.fleetmanagement.api_rest.business.exception.ValueNotFoundException;
import com.fleetmanagement.api_rest.persistence.entity.Taxi;
import com.fleetmanagement.api_rest.persistence.repository.TaxiRepository;
import com.fleetmanagement.api_rest.presentation.dto.TaxiDTO;
import com.fleetmanagement.api_rest.presentation.mapper.TaxiMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaxiServiceTest {

	@Mock
	private TaxiRepository taxiRepository;
	@Mock
	private TaxiMapper taxiMapper;
	@InjectMocks //injects the mocked dependencies into the service
	private TaxiService taxiService;

	List<Taxi> taxis;
	Pageable pageable;
	Page<Taxi> taxiPage;
	TaxiDTO taxi1;
	TaxiDTO taxi2;

	@BeforeEach
	public void setUp() {
		taxis = Arrays.asList(
				Taxi.builder().plate("ABC-123").build(),
				Taxi.builder().plate("abc-456").build());

		pageable = PageRequest.of(0, 10);
		taxiPage = new PageImpl<>(taxis, pageable, taxis.size());

		taxi1 = TaxiDTO.builder().plate("ABC-123").build();
		taxi2 = TaxiDTO.builder().plate("abc-456").build();
	}

	@Test
	@DisplayName("Testing method getTaxis() - It should return a Taxi list that match the plate string")
	public void getTaxisTest() {

		String plate = "ABC";

		when(taxiRepository.findByPlateContainingIgnoreCase(plate, pageable)).thenReturn(taxiPage);
		when(taxiMapper.toTaxiDTO(any(Taxi.class))).thenReturn(taxi1, taxi2);

		// Act
		List<TaxiDTO> taxiDTOList = taxiService.getTaxis(plate, 0, 10);

		System.out.println("T1 - Taxis");
		taxiDTOList.forEach(System.out::println);

		// Assert
		assertNotNull(taxiDTOList);
		assertEquals(2, taxiDTOList.size());
		verify(taxiRepository).findByPlateContainingIgnoreCase(plate, pageable); // Called once
		verify(taxiMapper, times(2)).toTaxiDTO(any(Taxi.class));
	}

	@Test
	@DisplayName("Testing method getTaxis() - It should return a page of taxis when the plate is empty")
	public void getTaxisNoPlateTest() {

		when(taxiRepository.findAll(pageable)).thenReturn(taxiPage);
		when(taxiMapper.toTaxiDTO(any(Taxi.class))).thenReturn(taxi1, taxi2);

		// Act
		List<TaxiDTO> taxiDTOList = taxiService.getTaxis(null, 0, 10);

		System.out.println("T2 - Taxis");
		taxiDTOList.forEach(System.out::println);

		// Assert
		assertNotNull(taxiDTOList);
		assertEquals(2, taxiDTOList.size());
		verify(taxiRepository).findAll(pageable); // Called once
		verify(taxiMapper, times(2)).toTaxiDTO(any(Taxi.class));
	}

	@Test
	@DisplayName("Testing method getTaxis() - It should thrown an exception when there are no matches for the plate ")
	public void getTaxisNotFoundTest() {

		String plate = "0000000";
		Page<Taxi> emptyPage = Page.empty(pageable);

		when(taxiRepository.findByPlateContainingIgnoreCase(plate, pageable)).thenReturn(emptyPage);

		System.out.println("T3 - Empty page");

		// Act & Assert
		ValueNotFoundException exception = assertThrows(ValueNotFoundException.class,
				() -> taxiService.getTaxis(plate, 0, 10));
		assertEquals("No taxis found with plate containing: " + plate, exception.getMessage());
	}

	@Test
	@DisplayName("Testing method getTaxis() - It should throw an exception for invalid page or limit")
	public void getTaxisInvalidPageTest() {
		// Arrange
		int invalidPage = -1;

		System.out.println("T4 - Invalid parameters");

		//Act-Assert
		InvalidParameterException exception = assertThrows(InvalidParameterException.class,
				() -> taxiService.getTaxis(null, invalidPage, 10));
		assertEquals("Page number cannot be negative", exception.getMessage());
	}

}
