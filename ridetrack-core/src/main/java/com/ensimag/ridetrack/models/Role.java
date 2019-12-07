package com.ensimag.ridetrack.models;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "role", uniqueConstraints = @UniqueConstraint(
	name = "UQ_ROLE_ROLE_NAME",
	columnNames = {"role_name"}
))
public class Role implements GrantedAuthority {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO, generator="native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id_role")
	private Long id;

	@Column(name = "role_name")
	private String name;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "role_privilege",
		joinColumns = @JoinColumn(
			name = "id_role",
			referencedColumnName = "id_role",
			foreignKey = @ForeignKey(name = "FK_ROLE_PRIVILEGE_ID_ROLE")),
		inverseJoinColumns = @JoinColumn(
			name = "id_privilege",
			referencedColumnName = "id_privilege",
			foreignKey = @ForeignKey(name = "FK_ROLE_PRIVILEGE_ID_PRIVILEGE")
		)
	)
	private Set<Privilege> privileges = new HashSet<>();

	public Role() {}

	public Role(String name) {
		this.name = name;
		this.privileges = new HashSet<>();
	}

	public static Role of(String roleName, String... privileges) {
		Role role = Role.of(roleName);
		Stream.of(privileges)
			.map(Privilege::of)
			.forEach(role::addPrivilege);
		return role;
	}

	public static Role of(String roleName) {
		return new Role(roleName);
	}


	public void addPrivilege(Privilege privilege) {
		if (privileges == null) {
			privileges = new HashSet<>();
		}
		privileges.add(privilege);
	}
	
	@Override
	public String getAuthority() {
		return name;
	}
}
