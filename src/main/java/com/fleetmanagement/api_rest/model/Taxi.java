package com.fleetmanagement.api_rest.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Represents a taxi entity that is mapped to the "taxis" table in the database.
 * This entity has two attributes: `id` (the primary key) and `plate` (the taxi's license plate).<br>
 * <p>
 * This class is managed by Hibernate through the Java Persistence API (JPA) and
 * can be used for CRUD operations within the Spring Boot framework.<br>
 */
@Entity
@Table(name = "taxis")
@Getter @Setter
public class Taxi {

	@Id
	private int id;

	@Column(name = "plate")
	private String plate;

	@OneToMany(mappedBy = "taxi_id", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonBackReference
	private List<Trajectory> trajectories;

	public Taxi() {
	}

}
