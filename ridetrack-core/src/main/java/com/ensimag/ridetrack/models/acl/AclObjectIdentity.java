package com.ensimag.ridetrack.models.acl;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.ensimag.ridetrack.models.Client;
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
	
	
	@OneToMany(mappedBy = "objectIdentity", orphanRemoval = true)
	private Set<AclEntry> aclEntries = new HashSet<>();
	
	public AclObjectIdentity() {
		// JPA constructor
	}
	
	public abstract Client getClient();
	
}
