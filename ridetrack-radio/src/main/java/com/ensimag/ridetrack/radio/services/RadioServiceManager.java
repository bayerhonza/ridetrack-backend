package com.ensimag.ridetrack.radio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ensimag.ridetrack.radio.packets.PacketQueueHandler;
import com.ridetrack.ridetrack.radio.RadioBrokerAuth;
import com.ridetrack.ridetrack.radio.RadioService;
import com.ridetrack.ridetrack.radio.RadioServiceProvider;
import com.ridetrack.ridetrack.radio.RadioType;
import com.ridetrack.ridetrack.radio.exceptions.RidetrackRadioException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RadioServiceManager {

    @Autowired
    private PacketQueueHandler packetQueueHandler;

    @Autowired
    private RadioServiceProviderManager manager;

    public RadioService getRadioService(RadioBrokerAuth auth, RadioType type) throws RidetrackRadioException {
        return createService(auth, type);
    }

    private RadioService createService(RadioBrokerAuth auth, RadioType type) throws RidetrackRadioException {
        log.info("Creating radio service for {}", type.name());
    
        if (type == RadioType.LORAWAN) {
            RadioServiceProvider provider = manager.getServiceProvider(type);
            return provider.createService(auth, packetQueueHandler);
        }
        log.error("Type {} not supported", type.name());
        throw new UnsupportedOperationException("Protol " + type.name() + " not supported");
    }
}
