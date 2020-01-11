package com.ensimag.ridetrack.radio.geoloc.loracloud;

import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ensimag.ridetrack.radio.geoloc.loracloud.dto.LocationRequestDTO;
import com.ensimag.ridetrack.radio.geoloc.loracloud.dto.ResponseDTO;

@Component
public class LoraCloudRestClient {
	
	public static final String LORA_CLOUD_URL = "https://gls.loracloud.com";
	
	private static final String LORA_CLOUD_TOKEN = "AQEAVrK5yjelPOofQIz8o2KbFX4OxsFvG2Pw4jLTup4MjPfV0lqU";
	
	private final HttpHeaders headers;
	
	private final RestTemplate restTemplate;
	
	public LoraCloudRestClient() {
		restTemplate = new RestTemplate();
		headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Ocp-Apim-Subscription-Key", LORA_CLOUD_TOKEN);
	}
	
	public ResponseDTO sendRequest(LocationRequestDTO req) {
		HttpEntity<LocationRequestDTO> requestEntity = new HttpEntity<>(req, headers);
		return restTemplate.postForObject(LORA_CLOUD_URL + "/api/v3/solve/singleframe", requestEntity, ResponseDTO.class);
	}
	
}
