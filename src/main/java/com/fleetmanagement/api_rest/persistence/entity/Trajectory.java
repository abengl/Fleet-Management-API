package com.fleetmanagement.api_rest.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "trajectories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trajectory {

	@Id
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "taxi_id", referencedColumnName = "id", nullable = false)
	private Taxi taxiId;

	@Column(name = "date")
	private Date date;

	@Column(name = "latitude")
	private double latitude;

	@Column(name = "longitude")
	private double longitude;
	
}

