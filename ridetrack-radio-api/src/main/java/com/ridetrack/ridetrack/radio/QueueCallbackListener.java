package com.ridetrack.ridetrack.radio;

public interface QueueCallbackListener {
    void processPacket(String messageType, byte[] packet);
}
