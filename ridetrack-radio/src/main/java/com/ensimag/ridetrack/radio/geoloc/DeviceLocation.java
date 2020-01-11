package com.ensimag.ridetrack.radio.geoloc;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class DeviceLocation {
	private String deviceUid;
	private Float latitude;
	private Float longitude;
	private Integer precision;
}
