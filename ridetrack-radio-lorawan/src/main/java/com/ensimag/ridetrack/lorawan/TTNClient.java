package com.ensimag.ridetrack.lorawan;

import com.ridetrack.ridetrack.radio.RadioBrokerAuth;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import static com.ensimag.ridetrack.lorawan.TtnMqttTopics.ACTIVATION;
import static com.ensimag.ridetrack.lorawan.TtnMqttTopics.UP_LINK_MESSAGE;

public class TTNClient extends MqttClient {


    private final RadioBrokerAuth auth;


    public TTNClient(RadioBrokerAuth auth) throws MqttException {
        super(auth.getHostname(), MqttClient.generateClientId(), new MemoryPersistence());
        this.auth = auth;
    }

    /**
     * Method for initializing and start of MQTT client
     *
     * @throws MqttException if problem while connecting
     */
    public void initAndStart(IMqttMessageListener listener) throws MqttException {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(auth.getUsername());
        options.setPassword(auth.getApiToken().toCharArray());
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);

        // connect to MQTT server
        connect(options);


        subscribe(ACTIVATION.getTopic(), listener);
        subscribe(UP_LINK_MESSAGE.getTopic(), listener);
    }
}
