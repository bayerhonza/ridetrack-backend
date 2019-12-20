package com.ensimag.ridetrack.models.acl;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.ensimag.ridetrack.models.RtUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@Entity
@Table(
		name = "user_group",
		uniqueConstraints = {
				@UniqueConstraint(name = "UQ_USER_GROUP", columnNames = { "name" })
		}
)
@PrimaryKeyJoinColumn(name = "id_user_group", foreignKey = @ForeignKey(name = "FK_UGROUP_SID"))
public class AclUserGroup extends AclSid {
	
	@Column(name = "name")
	private String name;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "user_group_user_group",
			joinColumns = @JoinColumn(
					name = "id_oid_user_group",
					foreignKey = @ForeignKey(name = "FK_USER_UGROUP_UGROUP_ID")),
			inverseJoinColumns = @JoinColumn(
					name = "id_user",
					foreignKey = @ForeignKey(name = "FK_USER_UGROUP_USER_ID"))
	)
	@Builder.Default
	private Set<RtUser> users = new HashSet<>();
	
	public AclUserGroup() {
		super(SidType.USER_GROUP);
	}
	
	public AclUserGroup(String name) {
		this();
		this.name = name;
	}
	
	public void addUser(RtUser user) {
		users.add(user);
	}
	
	public void removeUser(RtUser user) {
		users.remove(user);
	}
	
}
