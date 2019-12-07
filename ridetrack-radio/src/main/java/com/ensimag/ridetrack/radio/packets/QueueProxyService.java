package com.ensimag.ridetrack.radio.packets;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class QueueProxyService {

    private Map<String, Queue<RtPacket>> queueMap = new HashMap<>();

    public void registerQueue(String producer, Queue<RtPacket> queue) {
        queueMap.put(producer, queue);
    }

    public Queue<RtPacket> getNamedQueue(String producer) {
        return queueMap.get(producer);
    }
}
