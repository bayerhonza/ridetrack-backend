package com.ensimag.ridetrack.models;
import static com.ensimag.ridetrack.models.constants.RideTrackConstraint.UQ_USER_USERNAME;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@Table(
		name = "rt_users",
		uniqueConstraints = {
				@UniqueConstraint(name = UQ_USER_USERNAME, columnNames = { "username" })
		}
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class RtUser extends AbstractTimestampedEntity {
	
	@Id
	@Column(name = "id_user")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "user_sequence")
	@GenericGenerator(name = "user_sequence", strategy = "native")
	private Long userId;
	
	@Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9._-]{2,}$")
	@Size(max = 100)
	@Column(name = "username")
	private String username;
	
	@NotBlank(message = "password cannot be empty")
	@Column(name = "password")
	private String password;
	
	@Column(name = "enabled")
	private boolean enabled;
	
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
	@Builder.Default
	private Set<Role> roles = new HashSet<>();
	
	public RtUser() {
		// no-arg constructor
	}
}
