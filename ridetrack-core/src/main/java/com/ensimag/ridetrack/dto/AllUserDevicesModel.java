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
public class AllUserDevicesModel {
	private String clientName;
	private Set<DeviceGroupModel> deviceGroups;
	
}
