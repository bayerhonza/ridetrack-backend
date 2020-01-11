package com.ensimag.ridetrack.radio.packets;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ensimag.ridetrack.radio.geoloc.GeolocationService;
import com.ridetrack.ridetrack.radio.QueueCallbackListener;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PacketQueueHandler implements QueueCallbackListener {
    
    @Autowired
    private GeolocationService geolocationService;
    
    @Autowired
    private RawPacketHandler rawPacketHandler;
    
    private AtomicLong packetCounter = new AtomicLong(0);
    
    private BlockingQueue<RtPacket> packetQueue = new ArrayBlockingQueue<>(100);
    
    @Override
    public void processPacket(String topic, byte[] packet) {
        // put in queue only valuable packets
        if(!topic.contains("activations")) {
            queuePacket(topic, packet);
        }
        rawPacketHandler.processRawPayload(topic, packet);
    }

    public void queuePacket(String messageType, byte[] payload) {
        JSONObject root = new JSONObject(new String(payload));
        RtPacket rtPacket = new RtPacket();
        rtPacket.setPacketId(packetCounter.getAndIncrement());
        rtPacket.setTopic(messageType);
        rtPacket.setRawPayload(payload);
        rtPacket.setTimestamp(root.getJSONObject("metadata").getString("time"));
        log.debug("Queueing packet {}: {}", rtPacket.getPacketId(), new String(payload));
        packetQueue.add(rtPacket);
    }
    
    public BlockingQueue<RtPacket> getQueue() {
        return this.packetQueue;
    }

}
