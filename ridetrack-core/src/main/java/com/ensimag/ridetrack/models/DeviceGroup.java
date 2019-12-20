package com.ensimag.ridetrack.models;

import static com.ensimag.ridetrack.models.constants.RideTrackConstraint.UQ_DEVICE_GROUP_SPACE_GROUP_NAME;

import java.time.ZonedDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.ensimag.ridetrack.models.acl.AclObjectIdentity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@Entity
@Table(
		name = "device_group",
		uniqueConstraints = {
				@UniqueConstraint(
						name = UQ_DEVICE_GROUP_SPACE_GROUP_NAME, columnNames = { "name", "id_space" }),
		}
)
@PrimaryKeyJoinColumn(name = "id_dev_group", foreignKey = @ForeignKey(name = "FK_DEV_GROUP_OID"))
public class DeviceGroup extends AclObjectIdentity {

	
	@Column(name = "name")
	private String name;
	
	@NotNull
	@ManyToOne
	@Cascade(value = CascadeType.PERSIST)
	@JoinColumn(name = "id_space", foreignKey = @ForeignKey(name = "fk_devgroup_id_space"))
	private Space space;
	
	@OneToMany(mappedBy = "deviceGroup")
	@EqualsAndHashCode.Exclude
	private Set<Device> devices;

	@CreationTimestamp
	@Column(name = "created_at")
	protected ZonedDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	protected ZonedDateTime updatedAt;
	
	public DeviceGroup() {
        // no-arg constructor
	}
	
	public void addDevice(Device device) {
		this.devices.add(device);
	}
	
	public void remove(Device device) {
		this.devices.remove(device);
	}
	
	public Client getClient() {
		return space.getClient();
	}
	
}
