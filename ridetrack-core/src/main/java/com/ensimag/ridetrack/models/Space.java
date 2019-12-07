package com.ensimag.ridetrack.models;

import static com.ensimag.ridetrack.models.constants.RideTrackConstraint.UQ_SPACE_CLIENT_SPACE_NAME;

import java.time.ZonedDateTime;
import java.util.HashSet;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
		name = "space",
		uniqueConstraints = {
				@UniqueConstraint(name = UQ_SPACE_CLIENT_SPACE_NAME, columnNames = { "name", "client_id" })
		}
)

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Space {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id_space")
	private Long id;
	
	@CreationTimestamp
	@Column(name = "created_at")
	private ZonedDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private ZonedDateTime updatedAt;
	
	@NotBlank
	@Column(name = "name")
	private String name;
	
	@NotNull
	@ManyToOne
	@Cascade(value = CascadeType.PERSIST)
	@JoinColumn(name = "client_id", foreignKey = @ForeignKey(name = "fk_space_client_id"))
	private Client owner;
	
	@OneToMany(mappedBy = "space")
	@Builder.Default
	private Set<DeviceGroup> deviceGroups = new HashSet<>();
	
	@OneToMany(mappedBy = "space")
	private final Set<User> users = new HashSet<>();
	
	public void addDeviceGroup(DeviceGroup deviceGroup) {
		this.deviceGroups.add(deviceGroup);
	}
	
	@Override
	public String toString() {
		return "Space[name=" + name + ",owner=" + owner.getClientName() + "]";
	}
	
}
