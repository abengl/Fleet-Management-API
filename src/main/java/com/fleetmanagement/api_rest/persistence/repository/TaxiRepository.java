package com.fleetmanagement.api_rest.persistence.repository;

import com.fleetmanagement.api_rest.persistence.entity.TaxiEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxiRepository extends JpaRepository<TaxiEntity, Integer> {
	@Override
	boolean existsById(Integer integer);

	Page<TaxiEntity> findByPlateContainingIgnoreCase(String plate, Pageable pageable);

}
