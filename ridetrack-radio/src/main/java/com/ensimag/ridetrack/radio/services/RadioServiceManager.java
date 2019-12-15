package com.ensimag.ridetrack.radio.services;

import com.ensimag.ridetrack.radio.packets.PacketQueueHandler;
import com.ridetrack.ridetrack.radio.*;
import com.ridetrack.ridetrack.radio.exceptions.RidetrackRadioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RadioServiceManager {

    @Autowired
    private PacketQueueHandler packetQueueHandler;

    @Autowired
    private RadioServiceProviderManager manager;

    public RadioService getRadioService(RadioBrokerAuth auth, RadioType type) throws RidetrackRadioException {
        return createService(auth, type);
    }

    private RadioService createService(RadioBrokerAuth auth, RadioType type) throws RidetrackRadioException {
        if (type == RadioType.LORAWAN) {
            RadioServiceProvider provider = manager.getServiceProvider(type);
            return provider.createService(auth, packetQueueHandler);
        }
        throw new UnsupportedOperationException("Protol " + type.name() + " not supported");
    }
}
