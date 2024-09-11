package com.fleetmanagement.api_rest.persistence.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "taxis")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Taxi {

	@Id
	private Integer id;

	@Column(name = "plate")
	private String plate;

	@OneToMany(mappedBy = "taxiId", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Trajectory> trajectories;

}

