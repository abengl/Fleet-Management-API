package com.fleetmanagement.api_rest.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "trajectories")
@Getter
@Setter
public class Trajectory {

	@Id
	private Integer id;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "taxi_id", referencedColumnName = "id", nullable = false)
	private Taxi taxiId;

	@Column(name = "date")
	private Date date;

	@Column(name = "latitude")
	private double latitude;

	@Column(name = "longitude")
	private double longitude;

	public Trajectory() {
	}

	public Trajectory(Integer id, Taxi taxiId, Date date, double latitude, double longitude) {
		this.id = id;
		this.taxiId = taxiId;
		this.date = date;
		this.latitude = latitude;
		this.longitude = longitude;
	}
}

