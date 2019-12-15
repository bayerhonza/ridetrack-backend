package com.ridetrack.ridetrack.radio;

import com.ridetrack.ridetrack.radio.exceptions.RidetrackRadioException;

public interface RadioServiceProvider {

    RadioService createService(RadioBrokerAuth auth, QueueCallbackListener listener) throws RidetrackRadioException;

}
