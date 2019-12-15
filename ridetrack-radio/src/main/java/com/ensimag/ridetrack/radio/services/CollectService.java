package com.ensimag.ridetrack.radio.services;

import com.ridetrack.ridetrack.radio.RadioBrokerAuth;
import com.ridetrack.ridetrack.radio.RadioService;
import com.ridetrack.ridetrack.radio.exceptions.RidetrackRadioException;
import org.springframework.stereotype.Service;

import static com.ridetrack.ridetrack.radio.RadioType.LORAWAN;

@Service
public class CollectService {

    private RadioServiceManager radioServiceManager;

    public void testCollect() throws RidetrackRadioException {
        RadioBrokerAuth auth = RadioBrokerAuth.builder()
                .hostname("eu.thethings.network")
                .apiToken("ttn-account-v2.FnXxZ08ZXLja9Uc4yNCsPzdZxlwgrqEAMnLQX3CPgMg")
                .username("ridetrack")
                .build();
        RadioService ttnService = radioServiceManager.getRadioService(auth, LORAWAN);
        ttnService.startService();
    }
}
