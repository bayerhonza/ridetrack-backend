package com.ensimag.ridetrack.radio.geoloc.loracloud;
import java.util.stream.StreamSupport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ensimag.ridetrack.radio.geoloc.loracloud.dto.GatewayDTO;
import com.ensimag.ridetrack.radio.geoloc.loracloud.dto.LocationRequestDTO;
import com.ensimag.ridetrack.radio.geoloc.loracloud.dto.UplinkDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestMapper {
	
	public LocationRequestDTO mapTTNPacketToRequest(JSONObject ttnPacket) {
		LocationRequestDTO result = new LocationRequestDTO();
		JSONArray gateways = ttnPacket.getJSONObject("metadata").getJSONArray("gateways");
		StreamSupport.stream(gateways.spliterator(), false)
				.filter(o -> o instanceof JSONObject)
				.forEach(o -> parseGateway(result, (JSONObject) o));
		
		return result;
	}
	
	public void parseGateway(LocationRequestDTO request, JSONObject gateway) {
		try {
			GatewayDTO gatewayDTO = GatewayDTO.builder()
					.gatewayId(gateway.getString("gtw_id"))
					.altitude(gateway.getFloat("altitude"))
					.latitude(gateway.getFloat("latitude"))
					.longitude(gateway.getFloat("longitude"))
					.build();
			request.addGateway(gatewayDTO);
			
			UplinkDTO uplinkDTO = UplinkDTO.builder()
					.gatewayId(gateway.getString("gtw_id"))
					.antenna(gateway.has("antenna") ? gateway.getInt("antenna") : null)
					.rssi(gateway.getFloat("rssi"))
					.snr(gateway.getFloat("snr"))
					.tdoa(gateway.has("fine_timestamp") ? gateway.getInt("fine_timestamp") : null)
					.build();
			request.addUplink(uplinkDTO);
		} catch (JSONException ex) {
			log.error("Bad JSON structure. Skipping gateway");
		}
		
	}
	
}
