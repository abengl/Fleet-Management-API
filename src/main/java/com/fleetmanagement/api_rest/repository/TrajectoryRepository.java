package com.fleetmanagement.api_rest.repository;

import com.fleetmanagement.api_rest.model.Trajectory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TrajectoryRepository extends JpaRepository<Trajectory, Integer> {
	@Query("SELECT t FROM Trajectory t WHERE t.taxiId.id = :taxiId AND FUNCTION('DATE', t.date) = :date")
	Page<Trajectory> findByTaxiId_IdAndDate(Integer taxiId, @Param("date") Date date, Pageable pageable);

	Page<Trajectory> findAll(Pageable pageable);

	@Query("SELECT t FROM Trajectory t WHERE t.date = (SELECT MAX(t2.date) FROM Trajectory t2 WHERE t2.taxiId.id = t" +
			".taxiId.id)")
	Page<Trajectory> findLatestLocations(Pageable pageable);

}