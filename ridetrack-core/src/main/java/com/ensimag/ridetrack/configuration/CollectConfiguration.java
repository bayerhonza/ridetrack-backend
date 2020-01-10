package com.ensimag.ridetrack.configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.ensimag.ridetrack.radio.services.CollectService;
import com.ridetrack.ridetrack.radio.exceptions.RidetrackRadioException;

@Configuration
public class CollectConfiguration {
	
	@Value("${ridetrack.ttn.hostname}")
	private String hostname;
	
	@Value("${ridetrack.ttn.apikey}")
	private String apiKey;
	
	@Value("${ridetrack.ttn.username}")
	private String username;
	
	@Autowired
	private CollectService collectService;
	
	@Autowired
	public void startCollectServices() throws RidetrackRadioException {
		collectService.initAndLaunchLoraService(hostname, apiKey, username);
	}
}
