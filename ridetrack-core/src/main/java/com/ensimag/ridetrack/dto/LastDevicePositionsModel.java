package com.ensimag.ridetrack.dto;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LastDevicePositionsModel {
	private String clientName;
	private Set<DeviceGroupModel> deviceGroups;
	
}
