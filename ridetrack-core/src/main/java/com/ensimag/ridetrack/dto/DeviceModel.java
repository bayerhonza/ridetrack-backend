package com.ensimag.ridetrack.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class DeviceModel {
	private String deviceUid;
	private String name;
	private String deviceType;
	private Set<DeviceDataModel> data;
	
}