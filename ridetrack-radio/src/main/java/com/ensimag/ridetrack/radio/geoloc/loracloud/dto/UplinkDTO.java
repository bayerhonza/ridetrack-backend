package com.ensimag.ridetrack.radio.geoloc.loracloud.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UplinkDTO {
	private String gatewayId;
	private Integer antenna;
	private Integer tdoa;
	private Float rssi;
	private Float snr;
}
