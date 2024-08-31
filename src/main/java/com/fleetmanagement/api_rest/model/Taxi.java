package com.fleetmanagement.api_rest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents a taxi entity that is mapped to the "taxis" table in the database.
 * This entity has two attributes: `id` (the primary key) and `plate` (the taxi's license plate).<br>
 * <p>
 * This class is managed by Hibernate through the Java Persistence API (JPA) and
 * can be used for CRUD operations within the Spring Boot framework.<br>
 * <p>
 * Example usage:
 * <pre>
 *     Taxi taxi = new Taxi(1, "ABC-123");
 *     int id = taxi.getId();
 *     String plate = taxi.getPlate();
 * </pre>
 */
@Entity
@Table(name = "taxis")
public class Taxi {

	/**
	 * The primary key for the Taxi entity. This field uniquely identifies each taxi.
	 */
	@Id
	private int id;

	/**
	 * The license plate number of the taxi.
	 */
	private String plate;

	/**
	 * Default constructor required by Hibernate to create instances of the Taxi entity.
	 */
	public Taxi() {
	}

	/**
	 * Constructs a new Taxi with the specified id and plate.
	 *
	 * @param id    the unique identifier for the taxi
	 * @param plate the license plate number of the taxi
	 */
	public Taxi(int id, String plate) {
		this.id = id;
		this.plate = plate;
	}

	/**
	 * Gets the unique identifier of the taxi.
	 *
	 * @return the id of the taxi
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the unique identifier of the taxi.
	 *
	 * @param id the id to set for the taxi
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the license plate number of the taxi.
	 *
	 * @return the plate of the taxi
	 */
	public String getPlate() {
		return plate;
	}

	/**
	 * Sets the license plate number of the taxi.
	 *
	 * @param plate the plate to set for the taxi
	 */
	public void setPlate(String plate) {
		this.plate = plate;
	}

}
