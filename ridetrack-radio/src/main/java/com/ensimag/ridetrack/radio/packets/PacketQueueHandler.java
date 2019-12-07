package com.ensimag.ridetrack.radio.packets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Component
public class PacketQueueHandler {

    private final QueueProxyService queueProxy;

    private Queue<RtPacket> packetQueue = new LinkedList<>();

    public PacketQueueHandler(
            @Autowired QueueProxyService queueProxy) {
        this.queueProxy = queueProxy;
    }

    public void init() {

    }

    public void queuePacket(RtPacket packet) {
        packetQueue.add(packet);
    }


}
