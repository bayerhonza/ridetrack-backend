package com.ensimag.ridetrack.lorawan.mock;

import com.ridetrack.ridetrack.radio.RadioService;

public class MockTTNService implements RadioService {

    @Override
    public byte[] getNextPacket() {
        return new byte[0];
    }
}
