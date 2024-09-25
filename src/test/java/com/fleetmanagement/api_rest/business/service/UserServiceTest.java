package com.fleetmanagement.api_rest.business.service;

import com.fleetmanagement.api_rest.persistence.entity.RoleEntity;
import com.fleetmanagement.api_rest.persistence.entity.RoleEnum;
import com.fleetmanagement.api_rest.persistence.entity.UserEntity;
import com.fleetmanagement.api_rest.persistence.repository.RoleRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;

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
	PasswordEncoder passwordEncoder;
	@Mock
	RoleRepository roleRepository;
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
		List<UserEntity> userEntities = Arrays.asList(
				UserEntity.builder().name("U1").email("u1@mail").password("###").build(),
				UserEntity.builder().name("U2").email("u2@mail").password("###").build()
		);

		Pageable pageable = PageRequest.of(0, 10);
		Page<UserEntity> usersPage = new PageImpl<>(userEntities, pageable, userEntities.size());

		UserResponseDTO user1 = UserResponseDTO.builder().name("U1").email("u1@mail").build();
		UserResponseDTO user2 = UserResponseDTO.builder().name("U2").email("u2@mail").build();

		when(userRepository.findAll(pageable)).thenReturn(usersPage);
		when(userMapper.toUserResponseDTO(any(UserEntity.class))).thenReturn(user1, user2);

		//Act
		List<UserResponseDTO> userResponseDTOList = userService.getAllUsers(0, 10);

		System.out.println("T1 - Users");
		userResponseDTOList.forEach(System.out::println);

		//Assert
		Assertions.assertNotNull(userResponseDTOList);
		Assertions.assertEquals(2, userResponseDTOList.size());
		verify(userRepository).findAll(pageable);
		verify(userMapper, times(2)).toUserResponseDTO(any(UserEntity.class));
	}

	@Test
	@DisplayName("Testing createUser() - It should return a UserResponseDTO")
	void createUserTest() {
		UserEntity userEntity = UserEntity.builder().name("U1").email("u1@mail").password("###").build();
		UserResponseDTO userDTO = UserResponseDTO.builder().name("U1").email("u1@mail").build();
		UserCreateDTO userCreate = UserCreateDTO.builder().name("U1").email("u1@mail").password("###").build();

		when(userRepository.existsUserByEmail(anyString())).thenReturn(false);
		when(passwordEncoder.encode(anyString())).thenReturn("###");
		when(roleRepository.findByRoleEnum(any())).thenReturn(
				Optional.ofNullable(RoleEntity.builder().roleEnum(RoleEnum.USER).build()));
		when(userMapper.toUser(any(UserCreateDTO.class))).thenReturn(userEntity);
		when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
		when(userMapper.toUserResponseDTO(any(UserEntity.class))).thenReturn(userDTO);

		UserResponseDTO responseDTO = userService.createUser(userCreate);

		Assertions.assertNotNull(responseDTO);
		verify(userRepository).save(any(UserEntity.class));
		verify(userMapper).toUser(any(UserCreateDTO.class));
		verify(userMapper).toUserResponseDTO(any(UserEntity.class));
	}

	@Test
	@DisplayName("Testing deleteUser() - It should return a UserResponseDTO")
	void deleteUserTest() {
		Integer userId = 1;
		UserEntity userEntity = UserEntity.builder().name("U1").email("u1@mail").password("###").build();
		UserResponseDTO userDTO = UserResponseDTO.builder().name("U1").email("u1@mail").build();

		when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(userEntity));
		doNothing().when(userRepository).delete(userEntity);
		when(userMapper.toUserResponseDTO(any(UserEntity.class))).thenReturn(userDTO);

		UserResponseDTO responseDTO = userService.deleteUser(userId);

		Assertions.assertNotNull(responseDTO);
		verify(userRepository).findById(userId);
		verify(userRepository).delete(any(UserEntity.class));
		verify(userMapper).toUserResponseDTO(any(UserEntity.class));
	}

	@Test
	@DisplayName("Testing updateUserByName() - It should return a UserResponseDTO")
	void updateUserByNameTest() {
		Integer userId = 1;
		UserEntity userEntity = UserEntity.builder().name("U1").email("u1@mail").password("###").build();
		UserCreateDTO userCreate = UserCreateDTO.builder().name("U2").build();
		UserResponseDTO userDTO = UserResponseDTO.builder().name("U2").email("u1@mail").build();

		when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(userEntity));
		when(userMapper.toUserResponseDTO(any(UserEntity.class))).thenReturn(userDTO);

		UserResponseDTO responseDTO = userService.updateUserByName(userCreate, userId);
		System.out.println("T4 - User");
		System.out.println(responseDTO);

		Assertions.assertNotNull(responseDTO);
		verify(userRepository).findById(userId);
		verify(userMapper).toUserResponseDTO(any(UserEntity.class));
	}
}