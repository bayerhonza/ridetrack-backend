package com.ensimag.ridetrack.models;

import static com.ensimag.ridetrack.models.constants.RideTrackConstraint.UQ_DEVICE_DEVICE_UID;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.ensimag.ridetrack.models.acl.AclObjectIdentity;
import lombok.Getter;
import lombok.Setter;

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
	
	@Column(name = "status")
	private String status;
	
	@NotNull
	@Column(name = "name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "id_device_group", foreignKey = @ForeignKey(name = "fk_device_id_device_group"))
	private DeviceGroup deviceGroup;
	
	@OneToMany(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderColumn(name = "data_order_index")
	private List<DeviceData> deviceData = new ArrayList<>();

	@CreationTimestamp
	@Column(name = "created_at")
	protected ZonedDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	protected ZonedDateTime updatedAt;
	
	public Device() {
		// no-arg constructor
    }
    
    public void setDeviceGroup(DeviceGroup deviceGroup) {
		if (this.deviceGroup != null) {
			this.deviceGroup.remove(this);
		}
		this.deviceGroup = deviceGroup;
		deviceGroup.addDevice(this);
	}
	
	@Override
	public Client getClient() {
		return deviceGroup.getClient();
	}
	
	public void addDeviceData(DeviceData deviceData) {
		this.deviceData.add(deviceData);
	}
}
