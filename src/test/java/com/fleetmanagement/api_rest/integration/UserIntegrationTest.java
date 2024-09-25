package com.fleetmanagement.api_rest.integration;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
		"jwt.expiration.time=86400000",
		"jwt.private.key=test-key"
})
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("Integration testing to validate user creation and deletion")
	public void deleteUserTest() throws Exception {
		// Perform login as admin
		ResultActions loginResponse = mockMvc.perform(post("/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"email\":\"admin@test.com\",\"password\":\"test\"}"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk());

		// Create a new user
		ResultActions createUserResponse = mockMvc.perform(post("/users")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization",
								"Bearer " + loginResponse.andReturn().getResponse().getContentAsString())
						.content("{\"email\":\"newuser@test.com\",\"name\":\"newuser\",\"password\":\"password123\"," +
								"\"role\":\"DEVELOPER\"}"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isCreated());

		// Extract user ID from the response
		String responseBody = createUserResponse.andReturn().getResponse().getContentAsString();
		Integer userId = JsonPath.parse(responseBody).read("$.id", Integer.class);

		// Perform login as the new user
		ResultActions loginNewUserResponse = mockMvc.perform(post("/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"email\":\"newuser@test.com\",\"password\":\"password123\"}"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk());

		// Delete the newly created user
		ResultActions deleteUserResponse = mockMvc.perform(delete("/users/" + userId)
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization",
								"Bearer " + loginResponse.andReturn().getResponse().getContentAsString()))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk());

		// Attempt to login as the deleted user, expecting a Not Found status
		loginNewUserResponse = mockMvc.perform(post("/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"email\":\"newuser@test.com\",\"password\":\"password123\"}"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
