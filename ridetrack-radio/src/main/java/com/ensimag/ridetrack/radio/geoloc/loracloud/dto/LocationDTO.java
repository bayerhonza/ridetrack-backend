package com.ensimag.ridetrack.radio.geoloc.loracloud.dto;

import lombok.Getter;

@Getter
public class LocationDTO {
	private Float latitude;
	private Float longitude;
	private Integer toleranceHoriz;
}
