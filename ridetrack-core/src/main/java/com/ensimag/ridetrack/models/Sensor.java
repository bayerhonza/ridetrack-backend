package com.ensimag.ridetrack.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sensors")
public class Sensor extends AbstractTimestampedEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sensor_sequence")
	@GenericGenerator(name = "sensor_sequence", strategy = "native")
	@Column(name = "id_sensor")
	private Long id;
	
	@OneToOne(mappedBy = "sensor")
	private Device device;
	
	@NotBlank(message = "deviceUID may not be empty")
	@Column(name = "device_uid")
	private String deviceUid;
}
