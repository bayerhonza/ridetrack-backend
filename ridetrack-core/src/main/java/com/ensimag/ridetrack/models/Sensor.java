package com.ensimag.ridetrack.models;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.ensimag.ridetrack.models.acl.AclObjectIdentity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sensors")
@PrimaryKeyJoinColumn(name = "id_sensor", foreignKey = @ForeignKey(name = "FK_SENSOR_OID"))
public class Sensor extends AclObjectIdentity {
	
	@OneToOne(mappedBy = "sensor")
	private Device device;
	
	@NotBlank(message = "deviceUID may not be empty")
	@Column(name = "sensor_uid")
	private String sensorUid;

    @CreationTimestamp
    @Column(name = "created_at")
    protected ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    protected ZonedDateTime updatedAt;
	
	@Override
	public Client getClient() {
		return device.getClient();
	}
}
