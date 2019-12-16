package com.ensimag.ridetrack.models;

import static com.ensimag.ridetrack.models.constants.RideTrackConstraint.UQ_SPACE_CLIENT_SPACE_NAME;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.ensimag.ridetrack.models.acl.AclObjectIdentity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(
		name = "space",
		uniqueConstraints = {
				@UniqueConstraint(name = UQ_SPACE_CLIENT_SPACE_NAME, columnNames = { "name", "client_id" })
		}
)
@PrimaryKeyJoinColumn(name = "id_space", foreignKey = @ForeignKey(name = "FK_SENSOR_OID"))
public class Space extends AclObjectIdentity {

	@NotBlank
	@Column(name = "name")
	private String name;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "client_id", foreignKey = @ForeignKey(name = "fk_space_client_id"))
	private Client owner;
	
	@OneToMany(mappedBy = "space", cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private final Set<DeviceGroup> deviceGroups = new HashSet<>();
	
	@OneToMany(mappedBy = "space")
	@EqualsAndHashCode.Exclude
	private final Set<SpaceUser> users = new HashSet<>();

	@CreationTimestamp
	@Column(name = "created_at")
	protected ZonedDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	protected ZonedDateTime updatedAt;
	
	public void addDeviceGroup(DeviceGroup deviceGroup) {
		this.deviceGroups.add(deviceGroup);
	}
	
	public void addUser(SpaceUser user) {
		users.add(user);
		user.setSpace(this);
	}
	
	@Override
	public String toString() {
		return "Space[name=" + name + ",owner=" + owner.getClientName() + "]";
	}
	
	public Space() {
        // no-arg constructor
	}
	
	public void setOwner(Client owner) {
		this.owner = owner;
		owner.addSpace(this);
	}
}
