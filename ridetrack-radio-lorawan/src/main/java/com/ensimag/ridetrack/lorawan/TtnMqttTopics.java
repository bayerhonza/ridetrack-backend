package com.ensimag.ridetrack.lorawan;

public enum TtnMqttTopics {

    ACTIVATION("+/devices/+/events/activations"),
    UP_LINK_MESSAGE("ridetrack/devices/+/up");

    TtnMqttTopics(String topic) {
        this.topic = topic;
    }

    private String topic;

    public String getTopic() {
        return topic;
    }
}
