package com.ensimag.ridetrack.models;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(UserDevGroupConfigPK.class)
public class UserDevGroupConfig {

	@Id
	@Column(name = "id_user_config")
	private Long userConfigId;

	@CreationTimestamp
	@Column(name = "created_at")
	private ZonedDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private ZonedDateTime updatedAt;

	@Id
	@Column(name = "id_device_group")
	private Long devGroupId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_user_config", nullable = false, updatable = false, insertable = false)
	private UserConfiguration userConfiguration;

	@ManyToOne
	@JoinColumn(name = "id_device_group", nullable = false, updatable = false, insertable = false)
	private DeviceGroup deviceGroup;
	
}
