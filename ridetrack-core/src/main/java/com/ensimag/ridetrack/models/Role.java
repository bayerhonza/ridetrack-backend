package com.ensimag.ridetrack.models;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import javax.persistence.*;

import com.ensimag.ridetrack.models.acl.AclPrivilege;
import com.ensimag.ridetrack.models.acl.AclSid;
import com.ensimag.ridetrack.models.acl.SidType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;

import com.ensimag.ridetrack.privileges.PrivilegeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "role", uniqueConstraints = @UniqueConstraint(
	name = "UQ_ROLE_ROLE_NAME",
	columnNames = {"role_name"}
))
@PrimaryKeyJoinColumn(name = "id_role", foreignKey = @ForeignKey(name = "FK_ROLE_SID"))
public class Role extends AclSid implements GrantedAuthority {
	
	@Column(name = "role_name")
	private String name;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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
	private Set<AclPrivilege> aclPrivileges = new HashSet<>();
	
	@CreationTimestamp
	@Column(name = "created_at")
	private ZonedDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private ZonedDateTime updatedAt;
	
	public Role() {
		super(SidType.ROLE);
	}

	public Role(String name) {
		super(SidType.ROLE);
		this.name = name;
		this.aclPrivileges = new HashSet<>();
	}

	public static Role of(String roleName, AclPrivilege... privileges) {
		Role role = Role.of(roleName);
		Stream.of(privileges)
			.forEach(role::addPrivilege);
		return role;
	}

	public static Role of(String roleName) {
		return new Role(roleName);
	}


	public void addPrivilege(AclPrivilege aclPrivilege) {
		if (aclPrivileges == null) {
			aclPrivileges = new HashSet<>();
		}
		aclPrivileges.add(aclPrivilege);
	}
	
	@Override
	public String getAuthority() {
		return name;
	}

}
