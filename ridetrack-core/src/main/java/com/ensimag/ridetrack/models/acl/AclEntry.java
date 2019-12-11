package com.ensimag.ridetrack.models.acl;

import java.io.Serializable;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import com.ensimag.ridetrack.models.UserDevGroupConfigPK;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@Table(name = "acl_entry")
@IdClass(AclEntryPK.class)
public class AclEntry {
	
	@Id
	@Column(name = "oid")
	@Setter(AccessLevel.NONE)
	private Long oid;
	
	@Id
	@Column(name = "sid")
	@Setter(AccessLevel.NONE)
	private Long sid;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "oid",
			nullable = false,
			updatable = false,
			insertable = false,
			foreignKey = @ForeignKey(name = "FK_ACL_ENTRY_OID"))
	private AclObjectIdentity objectIdentity;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sid",
			nullable = false,
			updatable = false,
			insertable = false,
			foreignKey = @ForeignKey(name = "FK_ACL_ENTRY_SID"))
	private AclSid sidObject;
	
	@Column(name = "mask")
	private int mask;
	
	public AclEntry() {
	
	}
}
