package com.ensimag.ridetrack.lorawan;

import com.ridetrack.ridetrack.radio.RadioService;
import com.ridetrack.ridetrack.radio.RadioBrokerAuth;

public  class TTNServiceImpl implements RadioService {

	private TTNClient client;

	TTNServiceImpl(RadioBrokerAuth auth) {
		client = new TTNClient(auth);
	}

	@Override
	public byte[] getNextPacket() {
		return client.getPacketPayload();
	}
}
