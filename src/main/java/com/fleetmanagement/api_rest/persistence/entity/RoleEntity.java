package com.fleetmanagement.api_rest.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Entity representing a role in the system.
 * This entity is mapped to the 'roles' table in the 'api' schema.
 * It contains information about the role such as its ID and name.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles", schema = "api")
public class RoleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "role_name")
	@Enumerated(EnumType.STRING)
	private RoleEnum roleEnum;

}
