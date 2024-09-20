package com.fleetmanagement.api_rest.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trajectories", schema = "api")
public class TrajectoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "taxi_id", referencedColumnName = "id", nullable = false)
	private TaxiEntity taxiId;

	@Column(name = "date")
	private Date date;

	@Column(name = "latitude")
	private double latitude;

	@Column(name = "longitude")
	private double longitude;
	
}

