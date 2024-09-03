package com.fleetmanagement.api_rest.repository;

import com.fleetmanagement.api_rest.model.Taxi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxiRepository extends JpaRepository<Taxi, Integer> {
	Page<Taxi> findAll(Pageable pageable);
	Page<Taxi> findByPlateContainingIgnoreCase(String plate, Pageable pageable);
	boolean existsById(Integer id);
}
