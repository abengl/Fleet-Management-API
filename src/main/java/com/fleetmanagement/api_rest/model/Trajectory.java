package com.fleetmanagement.api_rest.model;

import jakarta.persistence.*; // for Spring Boot 3

import java.time.LocalDateTime;

/*data model class corresponds to entity and table trajectories
*
– @Entity annotation indicates that the class is a persistent Java class.
– @Table annotation provides the table that maps this entity.
– @Id annotation is for the primary key.
– @GeneratedValue annotation is used to define generation strategy for the primary key. GenerationType.AUTO means Auto Increment field.
– @Column annotation is used to define the column in database that maps annotated field.
* */

@Entity
@Table(name = "trajectories")
public class Trajectory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;


	/*
	* La variable taxi en la clase Trajectory representa la relación inversa, es decir, cada trayectoria pertenece a un único taxi. Esto permite asociar cada instancia de Trajectory con una instancia específica de Taxi.
	* Facilita la navegación en la dirección Trajectory -> Taxi, lo que significa que desde una trayectoria puedes acceder al taxi al que pertenece.
	* @JoinColumn(name = "taxi_id") indica que esta columna en trajectories actúa como clave foránea.
	* @JoinColumn indica que la variable taxi en la clase Trajectory está mapeada a una columna en la tabla trajectories que actúa como clave foránea. En este caso, la columna en la base de datos se llama taxi_id.
	* nullable = false: Esta propiedad asegura que la columna taxi_id no permita valores null, lo que significa que cada trayectoria debe estar asociada a un taxi existente (es decir, no puede haber una trayectoria sin un taxi).
	*/
	@ManyToOne
	@JoinColumn(name = "taxi_id", nullable = false)
	private Taxi taxi;

	@Column(name = "date")
	private LocalDateTime date;

	@Column(name = "latitude")
	private double latitude;

	@Column(name = "longitude")
	private double longitude;

	public Trajectory() {

	}

	public Trajectory(int id, Taxi taxi, LocalDateTime date, double latitude, double longitude) {
		this.id = id;
		this.taxi = taxi;
		this.date = date;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Taxi getTaxi() {
		return taxi;
	}

	public void setTaxi(Taxi taxi) {
		this.taxi = taxi;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "Trajectory{" +
				"id=" + id +
				", taxi=" + taxi +
				", date=" + date +
				", latitude=" + latitude +
				", longitude=" + longitude +
				'}';
	}
}

