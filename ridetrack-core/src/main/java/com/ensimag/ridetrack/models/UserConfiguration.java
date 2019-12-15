package com.ensimag.ridetrack.models;

import java.time.ZonedDateTime;
import java.util.HashSet;
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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

@Builder(toBuilder = true)
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_configuration")
public class UserConfiguration {

    @Id
	@Column(name = "id_configuration")
	@GeneratedValue(strategy= GenerationType.AUTO, generator="user_conf_sequence")
	@GenericGenerator(name = "user_conf_sequence", strategy = "native")
	private Long id;

	@OneToOne(mappedBy = "userConfiguration")
	private SpaceUser user;

	@OneToMany(mappedBy = "userConfiguration")
	@EqualsAndHashCode.Exclude
	@Builder.Default
	private Set<UserDevGroupConfig> devGroupConfig = new HashSet<>();

	@CreationTimestamp
	@Column(name = "created_at")
	protected ZonedDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	protected ZonedDateTime updatedAt;
	
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
