package com.ensimag.ridetrack.radio.geoloc.loracloud;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.ensimag.ridetrack.radio.geoloc.loracloud.dto.LocationRequestDTO;
import com.ensimag.ridetrack.radio.geoloc.loracloud.dto.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

class RequestMapperTest {
	
	@Test
	public void testRequestMapper() throws IOException {
		RequestMapper requestMapper = new RequestMapper();
		String jsonString = Files.readString(Paths.get("src/test/resources/test.json"));
		JSONObject root = new JSONObject(jsonString);
		LocationRequestDTO locationRequestDTO = requestMapper.mapTTNPacketToRequest(root);
		assertNotNull(locationRequestDTO.getFrame());
		assertEquals(3, locationRequestDTO.getGateways().size());
		assertEquals(1, locationRequestDTO.getFrame().get(0).get(1));
		assertEquals("eui-904d4afffeff4dd5", locationRequestDTO.getFrame().get(2).get(0));
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		System.out.println(mapper.writeValueAsString(locationRequestDTO));
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Ocp-Apim-Subscription-Key", "AQEAVrK5yjelPOofQIz8o2KbFX4OxsFvG2Pw4jLTup4MjPfV0lqU");
		HttpEntity<LocationRequestDTO> requestEntity = new HttpEntity<>(locationRequestDTO, headers);
		ResponseDTO responseDTO = restTemplate.postForObject("https://gls.loracloud.com/api/v3/solve/singleframe", requestEntity, ResponseDTO.class);
		System.out.println(mapper.writeValueAsString(responseDTO));
		
	}
}