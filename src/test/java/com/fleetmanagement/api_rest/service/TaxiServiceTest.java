package com.fleetmanagement.api_rest.service;

import com.fleetmanagement.api_rest.dto.TaxiDTO;
import com.fleetmanagement.api_rest.exception.InvalidLimitException;
import com.fleetmanagement.api_rest.exception.ValueNotFoundException;
import com.fleetmanagement.api_rest.mapper.TaxiMapper;
import com.fleetmanagement.api_rest.model.Taxi;
import com.fleetmanagement.api_rest.repository.TaxiRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaxiServiceTest {

	@Mock
	private TaxiRepository taxiRepository;
	@Mock
	private TaxiMapper taxiMapper;
	@InjectMocks //injects the mocked dependencies into the service
	private TaxiService taxiService;

	@Test
	@DisplayName("TaxiService - Testing method getTaxis() - It should return a Taxi list that match the plate string")
	public void TaxiService_getTaxis_ReturnListTaxis() {
		// Arrange
		String plate = "ABC";
		int page = 0;
		int limit = 10;

		List<Taxi> taxis = Arrays.asList(new Taxi(1, "ABC-123", null), new Taxi(2, "abc-456", null));

		Pageable pageable = PageRequest.of(page, limit);
		Page<Taxi> taxiPage = new PageImpl<>(taxis, pageable, taxis.size());

		when(taxiRepository.findByPlateContainingIgnoreCase(plate, pageable)).thenReturn(taxiPage);
		when(taxiMapper.toTaxiDTO(any(Taxi.class))).thenReturn(new TaxiDTO(1, plate), new TaxiDTO(2, "abc-456"));

		// Act
		List<TaxiDTO> taxiDTOList = taxiService.getTaxis(plate, page, limit);

		// Assert
		assertNotNull(taxiDTOList);
		assertEquals(2, taxiDTOList.size());
		verify(taxiRepository).findByPlateContainingIgnoreCase(plate, pageable); // Called once
		verify(taxiMapper, times(2)).toTaxiDTO(any(Taxi.class));
	}

	@Test
	@DisplayName("TaxiService - Testing method getTaxis() - It should return a page of taxis when the plate is empty")
	public void TaxiService_getTaxis_ReturnTaxiPage() {
		// Arrange
		int page = 0;
		int limit = 10;

		List<Taxi> taxis = Arrays.asList(new Taxi(1, "ABC-123", null), new Taxi(2, "abc-456", null));

		Pageable pageable = PageRequest.of(page, limit);
		Page<Taxi> taxiPage = new PageImpl<>(taxis, pageable, taxis.size());

		when(taxiRepository.findAll(pageable)).thenReturn(taxiPage);
		when(taxiMapper.toTaxiDTO(any(Taxi.class))).thenReturn(new TaxiDTO(1, "ABC-123"), new TaxiDTO(2, "abc-456"));

		// Act
		List<TaxiDTO> taxiDTOList = taxiService.getTaxis(null, page, limit);

		// Assert
		assertNotNull(taxiDTOList);
		assertEquals(2, taxiDTOList.size());
		verify(taxiRepository).findAll(pageable); // Called once
		verify(taxiMapper, times(2)).toTaxiDTO(any(Taxi.class));
	}

	@Test
	@DisplayName("TaxiService - Testing method getTaxis() - It should return an exception when there is no match the " + "plate " + "parameter")
	public void TaxiService_getTaxis_ReturnExceptionNotFound() {
		String plate = "00000000000";
		int page = 0;
		int limit = 10;
		Pageable pageable = PageRequest.of(page, limit);
		Page<Taxi> emptyPage = Page.empty(pageable);

		when(taxiRepository.findByPlateContainingIgnoreCase(plate, pageable)).thenReturn(emptyPage);

		// Act & Assert
		ValueNotFoundException exception = assertThrows(ValueNotFoundException.class,
				() -> taxiService.getTaxis(plate, page, limit));
		assertEquals("No taxis found with plate containing: " + plate, exception.getMessage());
	}

	@Test
	@DisplayName("TaxiService - Testing method getTaxis() - It should throw an exception when page or limit are " +
			"invalid")
	public void TaxiService_getTaxis_ReturnExceptionLimit() {
		// Arrange
		int invalidPage = -1;
		int limit = 10;

		//Act-Assert
		InvalidLimitException exception = assertThrows(InvalidLimitException.class,
				() -> taxiService.getTaxis(null, invalidPage, limit));
		assertEquals("Page number cannot be negative", exception.getMessage());
	}

}