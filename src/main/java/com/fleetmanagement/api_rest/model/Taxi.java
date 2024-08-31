package com.fleetmanagement.api_rest.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "taxis")
@Getter @Setter
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
}
