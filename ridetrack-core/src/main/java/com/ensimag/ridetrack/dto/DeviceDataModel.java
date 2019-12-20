package com.ensimag.ridetrack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class DeviceDataModel {
	private String deviceStatus;
	private String xCoordinate;
	private String yCoordinate;
	private String timestamp;
	
}
