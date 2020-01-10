package com.ensimag.ridetrack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LastDevicePositionDTO {
	private String deviceUid;
	private String name;
	private String deviceType;
	private DeviceDataModel lastPosition;
	
}
