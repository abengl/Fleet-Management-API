package com.fleetmanagement.api_rest.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

	/*@OneToMany(mappedBy = "taxiId", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Trajectory> trajectories;*/

}

