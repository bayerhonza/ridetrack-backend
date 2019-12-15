package com.ridetrack.ridetrack.radio;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RadioBrokerAuth {
    private String hostname;
    private int port;
    private String username;
    private String apiToken;


}
