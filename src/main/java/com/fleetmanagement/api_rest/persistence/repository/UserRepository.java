package com.fleetmanagement.api_rest.persistence.repository;

import com.fleetmanagement.api_rest.persistence.entity.Trajectory;
import com.fleetmanagement.api_rest.persistence.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	boolean existsUserByEmail(String email);

}
