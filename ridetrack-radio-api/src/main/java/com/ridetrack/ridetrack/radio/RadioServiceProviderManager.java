package com.ridetrack.ridetrack.radio;

import java.security.Provider;
import java.util.HashMap;
import java.util.Map;

public class RadioServiceProviderManager {


    private Map<String, RadioServiceProvider> providers = new HashMap<>();

    public RadioServiceProvider getServiceProvider(String type) {
        return providers.get(type);
    }

    private void registerRadioService(RadioType type, RadioServiceProvider provider) {
        providers.put(type.name(), provider);
    }

}
