package com.ensimag.ridetrack.radio.services;

import com.ensimag.ridetrack.lorawan.TTNServiceProviderImpl;
import com.ridetrack.ridetrack.radio.RadioServiceProvider;
import com.ridetrack.ridetrack.radio.RadioType;

import java.util.EnumMap;
import java.util.Map;

public class RadioServiceProviderManager {


    private Map<RadioType, RadioServiceProvider> providers = new EnumMap<>(RadioType.class);

    public RadioServiceProvider getServiceProvider(RadioType type) {
        return providers.get(type);
    }

    public void registerRadioService(RadioType type) {
        RadioServiceProvider provider;
        if (type == RadioType.LORAWAN) {
            provider = new TTNServiceProviderImpl();
        } else {
            throw new UnsupportedOperationException("Radio type not supported");
        }
        providers.put(type, provider);
    }

}
