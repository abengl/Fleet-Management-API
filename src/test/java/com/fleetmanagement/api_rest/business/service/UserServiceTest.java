package com.fleetmanagement.api_rest.business.service;

import com.fleetmanagement.api_rest.persistence.entity.User;
import com.fleetmanagement.api_rest.persistence.repository.UserRepository;
import com.fleetmanagement.api_rest.presentation.dto.UserCreateDTO;
import com.fleetmanagement.api_rest.presentation.dto.UserResponseDTO;
import com.fleetmanagement.api_rest.utils.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	@Mock
	UserRepository userRepository;
	@Mock
	UserMapper userMapper;
	@InjectMocks
	UserService userService;

	@BeforeEach
	void setUp() {
	}

	@Test
	@DisplayName("Testing getAllUsers() - It should return a List<UserResponseDTO>")
	void getAllUsersTest() {
		//Arrange
		List<User> users = Arrays.asList(
				User.builder().name("U1").email("u1@mail").password("###").build(),
				User.builder().name("U2").email("u2@mail").password("###").build()
		);

		Pageable pageable = PageRequest.of(0, 10);
		Page<User> usersPage = new PageImpl<>(users, pageable, users.size());

		UserResponseDTO user1 = UserResponseDTO.builder().name("U1").email("u1@mail").build();
		UserResponseDTO user2 = UserResponseDTO.builder().name("U2").email("u2@mail").build();

		when(userRepository.findAll(pageable)).thenReturn(usersPage);
		when(userMapper.toUserResponseDTO(any(User.class))).thenReturn(user1, user2);

		//Act
		List<UserResponseDTO> userResponseDTOList = userService.getAllUsers(0, 10);

		System.out.println("T1 - Users");
		userResponseDTOList.forEach(System.out::println);

		//Assert
		Assertions.assertNotNull(userResponseDTOList);
		Assertions.assertEquals(2, userResponseDTOList.size());
		verify(userRepository).findAll(pageable);
		verify(userMapper, times(2)).toUserResponseDTO(any(User.class));
	}

	@Test
	@DisplayName("Testing createUser() - It should return a UserResponseDTO")
	void createUserTest() {
		User user = User.builder().name("U1").email("u1@mail").password("###").build();
		UserResponseDTO userDTO = UserResponseDTO.builder().name("U1").email("u1@mail").build();
		UserCreateDTO userCreate = UserCreateDTO.builder().name("U1").email("u1@mail").password("###").build();

		when(userMapper.toUser(any(UserCreateDTO.class))).thenReturn(user);
		when(userRepository.save(any(User.class))).thenReturn(user);
		when(userMapper.toUserResponseDTO(any(User.class))).thenReturn(userDTO);

		UserResponseDTO responseDTO = userService.createUser(userCreate);

		System.out.println("T2 - User");
		System.out.println(responseDTO);

		Assertions.assertNotNull(responseDTO);
		verify(userRepository).save(any(User.class));
		verify(userMapper).toUser(any(UserCreateDTO.class));
		verify(userMapper).toUserResponseDTO(any(User.class));
	}

	@Test
	@DisplayName("Testing deleteUser() - It should return a UserResponseDTO")
	void deleteUserTest() {
		Integer userId = 1;
		User user = User.builder().name("U1").email("u1@mail").password("###").build();
		UserResponseDTO userDTO = UserResponseDTO.builder().name("U1").email("u1@mail").build();

		when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
		doNothing().when(userRepository).delete(user);
		when(userMapper.toUserResponseDTO(any(User.class))).thenReturn(userDTO);

		UserResponseDTO responseDTO = userService.deleteUser(userId);
		System.out.println("T3 - User");
		System.out.println(responseDTO);

		Assertions.assertNotNull(responseDTO);
		verify(userRepository).findById(userId);
		verify(userRepository).delete(any(User.class));
		verify(userMapper).toUserResponseDTO(any(User.class));
	}

	@Test
	@DisplayName("Testing updateUserByName() - It should return a UserResponseDTO")
	void updateUserByNameTest() {
		Integer userId = 1;
		User user = User.builder().name("U1").email("u1@mail").password("###").build();
		UserCreateDTO userCreate = UserCreateDTO.builder().name("U2").build();
		UserResponseDTO userDTO = UserResponseDTO.builder().name("U2").email("u1@mail").build();

		when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
		when(userMapper.toUserResponseDTO(any(User.class))).thenReturn(userDTO);

		UserResponseDTO responseDTO = userService.updateUserByName(userCreate, userId);
		System.out.println("T4 - User");
		System.out.println(responseDTO);

		Assertions.assertNotNull(responseDTO);
		verify(userRepository).findById(userId);
		verify(userMapper).toUserResponseDTO(any(User.class));
	}
}