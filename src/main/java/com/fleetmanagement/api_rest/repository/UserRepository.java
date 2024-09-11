package com.fleetmanagement.api_rest.repository;

import com.fleetmanagement.api_rest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	//void save(String name, String email, String password);

	@Override
	Optional<User> findById(Integer integer);

	boolean existsUserByEmail(String email);
	boolean existsUserById(Integer id);
}
