package com.fleetmanagement.api_rest.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Table(name = "taxis")
public class Taxi {

	@Id
	private Integer id;

	@Column(name = "plate")
	private String plate;

	@OneToMany(mappedBy = "taxiId", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Trajectory> trajectories;

	public Taxi() {
	}

	public Taxi(Integer id, String plate, List<Trajectory> trajectories) {
		this.id = id;
		this.plate = plate;
		this.trajectories = trajectories;
	}
}

