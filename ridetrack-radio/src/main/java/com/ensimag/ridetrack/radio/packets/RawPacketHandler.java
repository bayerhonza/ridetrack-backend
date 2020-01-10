package com.ensimag.ridetrack.radio.packets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ensimag.ridetrack.radio.mongo.MongoService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RawPacketHandler {
    
    @Autowired
    private MongoService mongoService;
    
    public void processRawPayload(String topic, byte[] payload) {
        log.debug("Saving packet to MongoDB (topic: {}, payload: {})", topic, new String(payload));
        // save to MongoDB
        mongoService.savePacket(topic, payload);
    }
}
