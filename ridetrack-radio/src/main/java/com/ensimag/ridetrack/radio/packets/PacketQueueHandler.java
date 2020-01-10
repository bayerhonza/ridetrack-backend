package com.ensimag.ridetrack.radio.packets;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ridetrack.ridetrack.radio.QueueCallbackListener;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PacketQueueHandler implements QueueCallbackListener {

    private AtomicLong packetCounter = new AtomicLong(0);

    @Autowired
    private RawPacketHandler rawPacketHandler;

    private Queue<RtPacket> packetQueue = new LinkedList<>();

    @Override
    public void processPacket(String topic, byte[] packet) {
        queuePacket(topic, packet);
        rawPacketHandler.processRawPayload(topic, packet);
    }

    public void queuePacket(String messageType, byte[] payload) {
        RtPacket rtPacket = new RtPacket();
        rtPacket.setPacketId(packetCounter.getAndIncrement());
        rtPacket.setTopic(messageType);
        rtPacket.setRawPayload(payload);
        log.debug("Queueing packet {}: {}", rtPacket.getPacketId(), new String(payload));
        packetQueue.add(rtPacket);
    }

}
