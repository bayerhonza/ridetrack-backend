package com.ridetrack.ridetrack.radio;

public interface RadioServiceProvider {

    public RadioService createService(RadioBrokerAuth auth);

    public RadioService createMockService();
}
