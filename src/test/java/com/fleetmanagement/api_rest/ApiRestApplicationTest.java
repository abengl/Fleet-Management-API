package com.fleetmanagement.api_rest;

import com.fleetmanagement.api_rest.controller.TaxiController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApiRestApplicationTest {
	@Autowired
	TaxiController taxiController;

	@Test
	public void contextLoads() {
		assertThat(taxiController).isNot(null);
	}
}