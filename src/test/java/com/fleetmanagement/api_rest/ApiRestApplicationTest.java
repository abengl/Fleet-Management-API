package com.fleetmanagement.api_rest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
		"jwt.expiration.time=86400000",
		"jwt.private.key=test-key"
})
class ApiRestApplicationTest {

	@Test
	public void contextLoads() {
	}

}