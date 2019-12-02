package com.ensimag.ridetrack.models;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_configuration")
public class UserConfiguration {

	@Id
	@Column(name = "id_configuration")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@CreationTimestamp
	@Column(name = "created_at")
	private ZonedDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private ZonedDateTime updatedAt;

	@OneToOne(mappedBy = "userConfiguration")
	private User user;

	@OneToMany(mappedBy = "userConfiguration")
	private Set<UserDevGroupConfig> devGroupConfig;

	public Set<DeviceGroup> getAllDeviceGroup() {
		return devGroupConfig.stream()
			.map(UserDevGroupConfig::getDeviceGroup)
			.collect(Collectors.toSet());
	}
}
