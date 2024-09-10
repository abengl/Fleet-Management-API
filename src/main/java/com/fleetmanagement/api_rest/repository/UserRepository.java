package com.fleetmanagement.api_rest.repository;

import com.fleetmanagement.api_rest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	//void save(String name, String email, String password);
	boolean existsByEmail(String email);

}
