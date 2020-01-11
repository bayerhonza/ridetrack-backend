package com.ensimag.ridetrack.radio.packets;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RtPacket {

    private byte[] rawPayload;
    private PacketType packetType;
    private String topic;
    private Long packetId;
    private String timestamp;
}
