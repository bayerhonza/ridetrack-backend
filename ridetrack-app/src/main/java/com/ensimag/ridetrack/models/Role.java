package com.ensimag.ridetrack.models;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;

import com.ensimag.ridetrack.models.acl.AclSid;
import com.ensimag.ridetrack.models.acl.SidType;
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
	}


	public static Role of(String roleName) {
		return new Role(roleName);
	}
	
	
	@Override
	public String getAuthority() {
		return name;
	}

}
