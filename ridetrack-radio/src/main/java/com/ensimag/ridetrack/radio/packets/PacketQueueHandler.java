package com.ensimag.ridetrack.radio.packets;

import com.ridetrack.ridetrack.radio.QueueCallbackListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class PacketQueueHandler implements QueueCallbackListener {

    private AtomicLong packetCounter = new AtomicLong(0);

    @Autowired
    private RawPacketHandler rawPacketHandler;

    private Queue<RtPacket> packetQueue = new LinkedList<>();

    @Override
    public void processPacket(String messageType, byte[] packet) {
        queuePacket(messageType, packet);
        rawPacketHandler.processRawPayload(messageType, packet);
    }

    public void queuePacket(String messageType, byte[] packet) {
        RtPacket rtPacket = new RtPacket();
        rtPacket.setPacketId(packetCounter.getAndIncrement());
        rtPacket.setTopic(messageType);
        rtPacket.setRawPayload(packet);
        packetQueue.add(rtPacket);
    }

}
