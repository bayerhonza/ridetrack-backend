package com.ensimag.ridetrack.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DeviceGroupModel {
	private String name;
	private List<LastDevicePositionDTO> devices;
}
