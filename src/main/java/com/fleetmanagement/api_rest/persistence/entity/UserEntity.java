package com.fleetmanagement.api_rest.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

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
	private String email; // This is the username

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

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "user_roles", schema = "api", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns =
	@JoinColumn(name = "role_id"))
	private Set<RoleEntity> roles = new HashSet<>();
}
