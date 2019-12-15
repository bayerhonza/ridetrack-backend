package com.ensimag.ridetrack.models.acl;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@Table(name = "acl_object_identity")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AclObjectIdentity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "acl_oid_sequence")
	@GenericGenerator(name = "acl_oid_sequence", strategy = "native")
	@Column(name = "oid")
	private Long oid;
	
	public AclObjectIdentity() {
	}
	
}
