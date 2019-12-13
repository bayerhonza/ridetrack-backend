package com.ensimag.ridetrack.lorawan;

import com.ensimag.ridetrack.lorawan.mock.MockTTNService;
import com.ridetrack.ridetrack.radio.RadioBrokerAuth;
import com.ridetrack.ridetrack.radio.RadioService;
import com.ridetrack.ridetrack.radio.RadioServiceProvider;

public class TTNServiceProviderImpl implements RadioServiceProvider {

    @Override
    public RadioService createService(RadioBrokerAuth auth) {
        return new TTNServiceImpl(auth);
    }

    @Override
    public RadioService createMockService() {
        return new MockTTNService();
    }
}
