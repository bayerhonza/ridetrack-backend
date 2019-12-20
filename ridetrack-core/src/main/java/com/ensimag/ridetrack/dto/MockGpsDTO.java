package com.ensimag.ridetrack.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MockGpsDTO {
	private String idDevice;
	private String timestamp;
	private String latitude;
	private String longitude;
	private String altitude;
	private String type;
	private String name;
	private String status;
	
}
