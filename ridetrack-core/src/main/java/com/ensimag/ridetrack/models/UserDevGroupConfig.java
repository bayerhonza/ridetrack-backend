package com.ensimag.ridetrack.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@IdClass(UserDevGroupConfigPK.class)
public class UserDevGroupConfig extends AbstractTimestampedEntity {

	@Id
	@Column(name = "id_user_config")
	private Long userConfigId;
	@Id
	@Column(name = "id_device_group")
	private Long devGroupId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_user_config", nullable = false, updatable = false, insertable = false)
	private UserConfiguration userConfiguration;

	@ManyToOne
	@JoinColumn(name = "id_device_group", nullable = false, updatable = false, insertable = false)
	private DeviceGroup deviceGroup;
	
	public UserDevGroupConfig() {
		// no-arg constructor
	}
	
}
