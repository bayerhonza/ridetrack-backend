package com.ensimag.ridetrack.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.ensimag.ridetrack.models.acl.AclObjectIdentity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "sensors")
@PrimaryKeyJoinColumn(name = "id_sensor", foreignKey = @ForeignKey(name = "FK_SENSOR_OID"))
public class Sensor extends AclObjectIdentity {
	
	@OneToOne(mappedBy = "sensor")
	private Device device;
	
	@NotBlank(message = "deviceUID may not be empty")
	@Column(name = "device_uid")
	private String deviceUid;

    @CreationTimestamp
    @Column(name = "created_at")
    protected ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    protected ZonedDateTime updatedAt;

}
