package com.ensimag.ridetrack.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDTO {
	
	private String deviceUid;
	
	private String deviceType;
	
	private String status;
	
	private String name;
	
}
