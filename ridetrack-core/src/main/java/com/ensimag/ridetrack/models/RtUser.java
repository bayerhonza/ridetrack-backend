package com.ensimag.ridetrack.models;
import static com.ensimag.ridetrack.models.constants.RideTrackConstraint.UQ_USER_USERNAME;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.ensimag.ridetrack.models.acl.AclOidUserGroup;
import com.ensimag.ridetrack.models.acl.AclSid;
import com.ensimag.ridetrack.models.acl.SidType;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@Table(
		name = "rt_users",
		uniqueConstraints = {
				@UniqueConstraint(name = UQ_USER_USERNAME, columnNames = { "username" })
		}
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "id_user", foreignKey = @ForeignKey(name = "FK_USER_SID"))
public abstract class RtUser extends AclSid  {
	
	@Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9._-]{2,}$")
	@Size(max = 100)
	@Column(name = "username")
	private String username;
	
	@NotBlank(message = "password cannot be empty")
	@Column(name = "password")
	private String password;
	
	@Column(name = "enabled")
	private boolean enabled = true;

	@CreationTimestamp
	@Column(name = "created_at")
	protected ZonedDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	protected ZonedDateTime updatedAt;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "user_role",
			joinColumns = @JoinColumn(
					name = "id_user",
					referencedColumnName = "id_user",
					foreignKey = @ForeignKey(name = "FK_USER_ROLE_ID_USER")),
			inverseJoinColumns = @JoinColumn(
					name = "id_role",
					referencedColumnName = "id_role",
					foreignKey = @ForeignKey(name = "FK_USER_ROLE_ID_ROLE"))
	)
	private final Set<Role> roles = new HashSet<>();
	
	@ManyToMany(mappedBy = "users")
	private Set<AclOidUserGroup> userGroups;
	
	public void addRole(Role role) {
		roles.add(role);
	}
	
	public RtUser(String username, String password) {
		this();
		this.username = username;
		this.password = password;
	}

	public RtUser() {
		super(SidType.USER);
	}
	
	public Long getUserId() {
		return getSid();
	}
}
