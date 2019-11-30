package com.ensimag.ridetrack.models;

import java.io.Serializable;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class UserDevGroupConfigPK implements Serializable {

	private final Long userConfigId;
	private final Long devGroupId;

	public UserDevGroupConfigPK(Long userConfigId, Long devGroupId) {
		this.devGroupId = devGroupId;
		this.userConfigId = userConfigId;
	}

}
