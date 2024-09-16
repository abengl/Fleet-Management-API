package com.fleetmanagement.api_rest.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fleetmanagement.api_rest.business.service.UserService;
import com.fleetmanagement.api_rest.presentation.dto.UserCreateDTO;
import com.fleetmanagement.api_rest.presentation.dto.UserResponseDTO;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserService userService;
	@Autowired
	private ObjectMapper objectMapper;

	private UserCreateDTO userCreateDTO;
	private UserResponseDTO userResponseDTO;
	private List<UserResponseDTO> userResponseDTOList;

	@BeforeEach
	public void init() {
		userCreateDTO = UserCreateDTO.builder().name("U1").email("u1@mail").password("###").build();
		userResponseDTO = UserResponseDTO.builder().id(1).name("U1").email("u1@mail").build();
		userResponseDTOList = Arrays.asList(
				userResponseDTO,
				UserResponseDTO.builder().id(2).name("U2").email("u2@mail").build());
	}

	@Test
	@DisplayName("Testing getUsers()- It should return ResponseEntity<UserResponseDTO>")
	void getUsersTest() throws Exception {
		when(userService.getAllUsers(0, 10)).thenReturn(userResponseDTOList);

		ResultActions response = mockMvc.perform(get("/users")
						.contentType(MediaType.APPLICATION_JSON)
						.param("page", "0")
						.param("limit", "10"))
				.andDo(MockMvcResultHandlers.print());

		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.length()").value(userResponseDTOList.size()));
	}

	@Test
	@DisplayName("Testing createUser() - It should return ResponseEntity<UserResponseDTO>")
	void createUserTest() throws Exception {
		when(userService.createUser(any())).thenReturn(userResponseDTO);

		ResultActions response = mockMvc.perform(post("/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(userCreateDTO)))
				.andDo(MockMvcResultHandlers.print());

		response.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(userResponseDTO.getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(userResponseDTO.getEmail())));
	}

	@Test
	@DisplayName("Testing updateUserName()- It should return ResponseEntity<UserResponseDTO>")
	void updateUserNameTest() throws Exception {
		UserCreateDTO userUpdate = UserCreateDTO.builder().name("User1").build();

		when(userService.updateUserByName(any(UserCreateDTO.class), eq(1))).thenReturn(userResponseDTO);

		ResultActions response = mockMvc.perform(patch("/users/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(userUpdate)))
				.andDo(MockMvcResultHandlers.print());

		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(userResponseDTO.getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(userResponseDTO.getEmail())));
	}


	@Test
	@DisplayName("Testing deleteUser() - It should return ResponseEntity<UserResponseDTO>")
	void deleteUserTest() throws Exception {
		when(userService.deleteUser(1)).thenReturn(userResponseDTO);

		ResultActions response = mockMvc.perform(delete("/users/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print());

		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(userResponseDTO.getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(userResponseDTO.getEmail())));
	}
}