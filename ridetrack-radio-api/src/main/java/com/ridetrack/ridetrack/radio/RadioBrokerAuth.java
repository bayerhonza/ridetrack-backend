package com.ridetrack.ridetrack.radio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RadioBrokerAuth {
    private String hostname;
    private int port;
    private String username;
    private String apiToken;


}
