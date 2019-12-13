package com.ensimag.ridetrack.radio.services;

import com.ridetrack.ridetrack.radio.*;

public class RadioServiceManager {

    private RadioServiceProviderManager manager;

    public RadioService getRadioService(RadioBrokerAuth auth, RadioType type) {
        return createService(auth, type);
    }

    public RadioService getMockRadioService(RadioType type) {
        return createService(null,type);
    }

    private RadioService createService(RadioBrokerAuth auth, RadioType type) {
        if (type == RadioType.LORAWAN) {
            RadioServiceProvider provider = manager.getServiceProvider(type.name());
            if (auth == null) {
                return provider.createMockService();
            }
            return provider.createService(auth);
        }
        throw new UnsupportedOperationException("Protol " + type.name() + " not supported");
    }
}
