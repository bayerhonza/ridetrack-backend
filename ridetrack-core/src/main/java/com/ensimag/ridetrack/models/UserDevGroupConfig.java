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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@IdClass(UserDevGroupConfigPK.class)
public class UserDevGroupConfig {

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

    @CreationTimestamp
    @Column(name = "created_at")
    protected ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    protected ZonedDateTime updatedAt;
	
	public UserDevGroupConfig() {
        // no-arg constructor
	}
}
