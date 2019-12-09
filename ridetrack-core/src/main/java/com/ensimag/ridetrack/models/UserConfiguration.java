package com.ensimag.ridetrack.models;

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

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_configuration")
public class UserConfiguration extends AbstractTimestampedEntity {

	@Id
	@Column(name = "id_configuration")
	@GeneratedValue(strategy= GenerationType.AUTO, generator="user_conf_sequence")
	@GenericGenerator(name = "user_conf_sequence", strategy = "native")
	private Long id;

	@OneToOne(mappedBy = "userConfiguration")
	private SpaceUser user;

	@OneToMany(mappedBy = "userConfiguration")
	@EqualsAndHashCode.Exclude
	private Set<UserDevGroupConfig> devGroupConfig;
	
	public UserConfiguration() {
		// no-arg constructor
	}

	public Set<DeviceGroup> getAllDeviceGroup() {
		return devGroupConfig.stream()
			.map(UserDevGroupConfig::getDeviceGroup)
			.collect(Collectors.toSet());
	}
	
	@Override
	public String toString() {
		return "UserConfiguration{" + "createdAt=" + createdAt + ", user=" + user + '}';
	}
}
