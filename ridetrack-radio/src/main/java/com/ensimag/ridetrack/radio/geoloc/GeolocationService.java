package com.ensimag.ridetrack.radio.geoloc;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ensimag.ridetrack.radio.geoloc.loracloud.LoraCloudRestClient;
import com.ensimag.ridetrack.radio.geoloc.loracloud.RequestMapper;
import com.ensimag.ridetrack.radio.geoloc.loracloud.dto.LocationDTO;
import com.ensimag.ridetrack.radio.geoloc.loracloud.dto.LocationRequestDTO;
import com.ensimag.ridetrack.radio.geoloc.loracloud.dto.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GeolocationService {
	
	private RequestMapper requestMapper;
	
	private final ObjectMapper objectMapper;
	
	@Autowired
	private LoraCloudRestClient loraCloudClient;
	
	public GeolocationService() {
		objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		requestMapper = new RequestMapper();
	}
	
	@SneakyThrows
	public DeviceLocation locateTTNPacket(byte[] payload) {
		
		String payloadString = new String(payload);
		String deviceUid = getDeviceUidFromPayload(payloadString);
		LocationRequestDTO locationRequestDTO = requestMapper.mapTTNPacketToRequest(new JSONObject(payloadString));
		ResponseDTO response = loraCloudClient.sendRequest(locationRequestDTO);
		log.debug("Result for device '{}': '{}'", deviceUid, objectMapper.writeValueAsString(response));
		LocationDTO locationDTO = response.getResult().getLocationEst();
		return DeviceLocation.builder()
				.deviceUid(deviceUid)
				.latitude(locationDTO.getLatitude())
				.longitude(locationDTO.getLongitude())
				.precision(locationDTO.getToleranceHoriz())
				.build();
		
	}
	
	private String getDeviceUidFromPayload(String payloadString) {
		JSONObject jsonObject = new JSONObject(payloadString);
		return jsonObject.getString("hardware_serial");
	}
}
