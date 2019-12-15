package com.ridetrack.ridetrack.radio;

import com.ridetrack.ridetrack.radio.exceptions.RidetrackRadioException;

public interface RadioService {

    void startService() throws RidetrackRadioException;

    void stopService() throws RidetrackRadioException;
}
