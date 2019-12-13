package com.ensimag.ridetrack.radio.services;

import com.ridetrack.ridetrack.radio.RadioService;

public class TTNRadioServiceImpl implements RadioService {

    private RadioService ttnServiceImpl;

    @Override
    public byte[] getNextPacket() {
        return ttnServiceImpl.getNextPacket();
    }
}
