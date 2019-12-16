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
import java.util.HashSet;
import java.util.Set;

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
	
	@NotNull
	@Column(name = "type")
	private String deviceType;
	
	@NotNull
	@Column(name = "name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "id_device_group", foreignKey = @ForeignKey(name = "fk_device_id_device_group"))
	private DeviceGroup deviceGroup;
	
	@OneToMany(mappedBy = "device")
	private Set<DeviceData> deviceData = new HashSet<>();

	@CreationTimestamp
	@Column(name = "created_at")
	protected ZonedDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	protected ZonedDateTime updatedAt;
	
	public Device() {
		// no-arg constructor
    }
    
    public void addDeviceData(DeviceData deviceData) {
		this.deviceData.add(deviceData);
	}
}
