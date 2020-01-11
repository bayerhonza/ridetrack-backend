package com.ensimag.ridetrack.radio.geoloc.loracloud.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class GatewayDTO {
	
	private String gatewayId;
	private Float latitude;
	private Float longitude;
	private Float altitude;
	
}
