package com.ensimag.ridetrack.radio.services;

import static com.ridetrack.ridetrack.radio.RadioType.LORAWAN;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ridetrack.ridetrack.radio.RadioBrokerAuth;
import com.ridetrack.ridetrack.radio.RadioService;
import com.ridetrack.ridetrack.radio.exceptions.RidetrackRadioException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CollectService {
    
    @Autowired
    private RadioServiceManager radioServiceManager;

    public void initAndLaunchLoraService(String hostname, String apiToken, String username) throws RidetrackRadioException {
        RadioBrokerAuth auth = RadioBrokerAuth.builder()
                .hostname(hostname)
                .apiToken(apiToken)
                .username(username)
                .build();
        RadioService radioService = radioServiceManager.getRadioService(auth, LORAWAN);
        log.info("Starting radio service {}", LORAWAN.name());
        radioService.startService();
    }
}
