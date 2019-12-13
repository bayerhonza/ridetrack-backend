package com.ensimag.ridetrack.lorawan;
import com.ridetrack.ridetrack.radio.RadioBrokerAuth;

public class TTNClient    {

	private RadioBrokerAuth auth;

	public TTNClient(RadioBrokerAuth auth) {
		//super(auth.getHostname() + ":" +auth.getPort(), auth.getUsername(), auth.getApiToken());
		this.auth = auth;
	}

	public void init() {

	}

	public byte[] getPacketPayload() {
	    return null;
    }
}
