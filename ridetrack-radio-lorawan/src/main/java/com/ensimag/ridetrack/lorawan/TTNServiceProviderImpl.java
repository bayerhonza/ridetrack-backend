package com.ensimag.ridetrack.lorawan;

import com.ridetrack.ridetrack.radio.QueueCallbackListener;
import com.ridetrack.ridetrack.radio.RadioBrokerAuth;
import com.ridetrack.ridetrack.radio.RadioService;
import com.ridetrack.ridetrack.radio.RadioServiceProvider;
import com.ridetrack.ridetrack.radio.exceptions.RidetrackRadioException;


public class TTNServiceProviderImpl implements RadioServiceProvider {

    @Override
    public RadioService createService(RadioBrokerAuth auth, QueueCallbackListener listener) throws RidetrackRadioException {
        return new TTNServiceImpl(auth,listener);
    }

}
