package com.ensimag.ridetrack.radio.geoloc.loracloud.dto;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationRequestDTO {
	
	private List<GatewayDTO> gateways;
	
	private List<List<Object>> frame;
	
	public LocationRequestDTO() {
		gateways = new ArrayList<>();
		frame = new ArrayList<>();
	}
	
	public void addGateway(GatewayDTO gatewayDTO) {
		gateways.add(gatewayDTO);
	}
	
	public void addUplink(UplinkDTO uplinkDTO) {
		List<Object> uplinkList = new ArrayList<>();
		uplinkList.add(uplinkDTO.getGatewayId());
		uplinkList.add(uplinkDTO.getAntenna());
		uplinkList.add(uplinkDTO.getTdoa());
		uplinkList.add(uplinkDTO.getRssi());
		uplinkList.add(uplinkDTO.getSnr());
		frame.add(uplinkList);
	}
}
