package com.ensimag.ridetrack.radio.geoloc.loracloud.dto;
import java.util.List;

import lombok.Getter;

@Getter
public class ResponseDTO {
	private ResultDTO result;
	private List<String> warnings;
	
}
