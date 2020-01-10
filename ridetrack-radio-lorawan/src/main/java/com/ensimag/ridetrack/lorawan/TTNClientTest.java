package com.ensimag.ridetrack.lorawan;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.ridetrack.ridetrack.radio.RadioBrokerAuth;

class TTNClientTest {
	
	private Object lock = new Object();
	
	private Integer number = 0;
	
	public void runTest() throws MqttException, InterruptedException {
		RadioBrokerAuth auth = new RadioBrokerAuth();
		auth.setHostname("tcp://eu.thethings.network");
		auth.setPort(1883);
		auth.setUsername("ridetrack");
		auth.setApiToken("ttn-account-v2.FnXxZ08ZXLja9Uc4yNCsPzdZxlwgrqEAMnLQX3CPgMg");
		
		TTNClient ttnClient = new TTNClient(auth);
		ttnClient.initAndStart(this::packetHandler);
		while (true) {
			synchronized (lock) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("processed packet number " + ++number);
		}
	}
	
	public static void main(String[] args) throws MqttException, InterruptedException {
		TTNClientTest app = new TTNClientTest();
		app.runTest();
	}
	
	private void packetHandler(String topic, MqttMessage mqttMessage) {
		synchronized (lock) {
			lock.notify();
		}
		byte[] payload = mqttMessage.getPayload();
		String string = new String(payload);
		System.out.println("topic: " + topic + " message: " + string);
	}
}