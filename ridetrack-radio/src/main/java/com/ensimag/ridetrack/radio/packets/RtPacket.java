package com.ensimag.ridetrack.radio.packets;

import lombok.Getter;

@Getter
public class RtPacket {

    private byte[] rawPayload;
    private PacketType packetType;
    private Long packetId;
}
