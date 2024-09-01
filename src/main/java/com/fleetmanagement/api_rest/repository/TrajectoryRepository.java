package com.fleetmanagement.api_rest.repository;

import com.fleetmanagement.api_rest.model.Trajectory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TrajectoryRepository extends JpaRepository<Trajectory, Integer> {
	Page<Trajectory> findByTaxiId_IdAndDate(Integer taxiId, Date date, Pageable pageable);
}
