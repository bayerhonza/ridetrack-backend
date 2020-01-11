package com.ensimag.ridetrack.models.acl;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;

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
public class AclEntry implements GrantedAuthority {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "acl_entry_sequence")
	@GenericGenerator(name = "acl_entry_sequence", strategy = "native")
	@Column(name = "acl_entry_id")
	private Long aclEntryId;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "oid",
			nullable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_ACL_ENTRY_OID"))
	@OnDelete(action = OnDeleteAction.CASCADE)
	private AclObjectIdentity objectIdentity;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "sid",
			nullable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_ACL_ENTRY_SID"))
	@OnDelete(action = OnDeleteAction.CASCADE)
	private AclSid sidObject;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(
			name = "privilege_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_ACL_PRIVILEGE")
	)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private AclPrivilege privilege;
	
	public AclEntry() {
		// JPA constructor
	}
	
	@Override
	public String getAuthority() {
		return privilege.getPrivilegeName() + "_" + objectIdentity.getOid();
	}
}
