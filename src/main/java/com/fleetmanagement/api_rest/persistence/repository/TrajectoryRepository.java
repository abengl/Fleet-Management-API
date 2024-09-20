package com.fleetmanagement.api_rest.persistence.repository;

import com.fleetmanagement.api_rest.persistence.entity.TrajectoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TrajectoryRepository extends JpaRepository<TrajectoryEntity, Integer> {

	@Query("SELECT t FROM TrajectoryEntity t WHERE t.taxiId.id = :taxiId AND FUNCTION('DATE', t.date) = :date")
	Page<TrajectoryEntity> findByTaxiId_IdAndDate(Integer taxiId, @Param("date") Date date, Pageable pageable);

	@Query("SELECT t FROM TrajectoryEntity t WHERE t.date = (SELECT MAX(t2.date) FROM TrajectoryEntity t2 WHERE t2" +
			".taxiId.id = t" +
			".taxiId.id)")
	Page<TrajectoryEntity> findLatestLocations(Pageable pageable);

}