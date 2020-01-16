package com.ensimag.ridetrack.dto;

import java.util.List;

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
	private String deviceStatus;
	private List<DeviceDataModel> data;
	
}
