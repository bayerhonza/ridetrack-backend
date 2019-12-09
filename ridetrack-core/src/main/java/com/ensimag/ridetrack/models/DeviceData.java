package com.ensimag.ridetrack.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "device_data")
public class DeviceData extends AbstractTimestampedEntity {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO, generator="device_data_sequence")
	@GenericGenerator(name = "device_data_sequence", strategy = "native")
	@Column(name = "id_device_data")
	private Long id;

	@Column(name = "x_coord")
	private Long xCoord;

	@Column(name = "y_coord")
	private Long yCoord;

	@Column(name = "z_coord")
	private Long zCoord;

	@OneToOne
	@JoinColumn(name = "id_device", foreignKey = @ForeignKey(name = "fk_device_data_device_id"))
	private Device device;

}
