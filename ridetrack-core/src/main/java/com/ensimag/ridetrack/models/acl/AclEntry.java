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
public class AclEntry {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "acl_entry_sequence")
	@GenericGenerator(name = "acl_entry_sequence", strategy = "native")
	@Column(name = "acl_entry_id")
	private Long aclEntryId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "oid",
			nullable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_ACL_ENTRY_OID"))
	private AclObjectIdentity objectIdentity;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "sid",
			nullable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_ACL_ENTRY_SID"))
	private AclSid sidObject;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(
			name = "privilege_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_ACL_PRIVILEGE")
	)
	private AclPrivilege privilege;
	
	public AclEntry() {
	
	}
}
