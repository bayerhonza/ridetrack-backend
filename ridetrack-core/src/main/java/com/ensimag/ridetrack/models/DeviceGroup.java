package com.ensimag.ridetrack.models;

import static com.ensimag.ridetrack.models.constants.RideTrackConstraint.UQ_DEVICE_GROUP_SPACE_GROUP_NAME;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Builder
@AllArgsConstructor
@Entity
@Table(
		name = "device_group",
		uniqueConstraints = {
				@UniqueConstraint(
						name = UQ_DEVICE_GROUP_SPACE_GROUP_NAME, columnNames = { "name", "id_space" }),
		}
)
public class DeviceGroup extends AbstractTimestampedEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "device_group_sequence")
	@GenericGenerator(name = "device_group_sequence", strategy = "native")
	@Column(name = "id_dev_group")
	private Long id;
	
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
	
	public DeviceGroup() {
		// no-arg constructor
	}
	
}
