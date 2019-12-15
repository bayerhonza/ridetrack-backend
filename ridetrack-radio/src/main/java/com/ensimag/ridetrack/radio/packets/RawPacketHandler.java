package com.ensimag.ridetrack.radio.packets;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RawPacketHandler {

    @Autowired
    private MongoClient mongoClient;

    public void processRawPayload(String messageType, byte[] payload) {
        // save to MongoDB
    }
}
