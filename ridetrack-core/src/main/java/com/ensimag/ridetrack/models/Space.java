package com.ensimag.ridetrack.models;

import static com.ensimag.ridetrack.models.constants.RideTrackConstraint.UQ_SPACE_CLIENT_SPACE_NAME;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class Space extends AbstractTimestampedEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "space_sequence")
	@GenericGenerator(name = "space_sequence", strategy = "native")
	@Column(name = "id_space")
	private Long id;
	
	@NotBlank
	@Column(name = "name")
	private String name;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "client_id", foreignKey = @ForeignKey(name = "fk_space_client_id"))
	private Client owner;
	
	@OneToMany(mappedBy = "space", cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	@Builder.Default
	private Set<DeviceGroup> deviceGroups = new HashSet<>();
	
	@OneToMany(mappedBy = "space")
	@EqualsAndHashCode.Exclude
	private final Set<SpaceUser> users = new HashSet<>();
	
	public void addDeviceGroup(DeviceGroup deviceGroup) {
		this.deviceGroups.add(deviceGroup);
	}
	
	public void addUser(SpaceUser user) {
		users.add(user);
	}
	
	@Override
	public String toString() {
		return "Space[name=" + name + ",owner=" + owner.getClientName() + "]";
	}
	
	public Space() {
		// no-arg constructor
	}
	
}
