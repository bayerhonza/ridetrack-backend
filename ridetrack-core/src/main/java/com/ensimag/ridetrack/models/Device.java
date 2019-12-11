package com.ensimag.ridetrack.models;

import static com.ensimag.ridetrack.models.constants.RideTrackConstraint.UQ_DEVICE_DEVICE_UID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.ensimag.ridetrack.models.acl.AclObjectIdentity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Setter
@Getter
@Entity
@Table(
        name = "device",
        uniqueConstraints = {
                @UniqueConstraint(name = UQ_DEVICE_DEVICE_UID, columnNames = { "device_uid" })
        }
)
@PrimaryKeyJoinColumn(name = "id_device", foreignKey = @ForeignKey(name = "FK_DEVICE_OID"))
public class Device extends AclObjectIdentity {
	
	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(
			name = "id_sensor",
			referencedColumnName = "id_sensor",
			foreignKey = @ForeignKey(name = "fk_device_sensor")
	)
	private Sensor sensor;
	
	@NotNull
	@Column(name = "device_uid")
    private String deviceUid;
	
	@ManyToOne
	@JoinColumn(name = "id_device_group", foreignKey = @ForeignKey(name = "fk_device_id_device_group"))
	private DeviceGroup deviceGroup;
	
	@OneToOne(mappedBy = "device")
	private DeviceData deviceData;

	@CreationTimestamp
	@Column(name = "created_at")
	protected ZonedDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	protected ZonedDateTime updatedAt;
	
	public Device() {
		// no-arg constructor
    }
}
