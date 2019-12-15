package com.ensimag.ridetrack.lorawan;

import com.ridetrack.ridetrack.radio.QueueCallbackListener;
import com.ridetrack.ridetrack.radio.RadioBrokerAuth;
import com.ridetrack.ridetrack.radio.RadioService;
import com.ridetrack.ridetrack.radio.exceptions.RidetrackRadioException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class TTNServiceImpl implements RadioService {

    private TTNClient client;
    private QueueCallbackListener listener;

    TTNServiceImpl(RadioBrokerAuth auth, QueueCallbackListener listener) throws RidetrackRadioException {
        this.listener = listener;
        try {
            client = new TTNClient(auth);
        } catch (MqttException ex) {
            throw new RidetrackRadioException(ex);
        }
    }

    @Override
    public void startService() throws RidetrackRadioException {
        try {
            client.initAndStart(this::packetHandler);
        }catch (MqttException ex) {
            throw new RidetrackRadioException(ex);
        }
    }

    @Override
    public void stopService() throws RidetrackRadioException {
        try {
            client.close();
        } catch (MqttException ex) {
            throw new RidetrackRadioException(ex);
        }
    }

    private void packetHandler(String topic, MqttMessage message) {
        listener.processPacket(topic, message.getPayload());
    }
}
