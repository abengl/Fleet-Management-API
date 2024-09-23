package com.fleetmanagement.api_rest.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a user in the system.
 * This entity is mapped to the 'users' table in the 'api' schema.
 * It contains information about the user such as their ID, name, email, password, and account status.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", schema = "api")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email; // username

	@Column(name = "password")
	private String password;

	@Column(name = "is_enabled")
	private boolean isEnabled;

	@Column(name = "account_non_Expired")
	private boolean accountNonExpired;

	@Column(name = "account_non_Locked")
	private boolean accountNonLocked;

	@Column(name = "credentials_non_Expired")
	private boolean credentialsNonExpired;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = RoleEntity.class, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "role_id")
	private RoleEntity role;
}
