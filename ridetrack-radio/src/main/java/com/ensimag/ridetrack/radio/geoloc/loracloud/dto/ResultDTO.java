package com.ensimag.ridetrack.radio.geoloc.loracloud.dto;

import lombok.Getter;

@Getter
public class ResultDTO {
	private Integer numUsedGateways;
	private Float HDOP;
	private String algorithmType;
	private LocationDTO locationEst;
}
