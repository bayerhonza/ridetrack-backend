package com.ensimag.ridetrack.controllers;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thethingsnetwork.data.common.Connection;
import org.thethingsnetwork.data.common.messages.ActivationMessage;
import org.thethingsnetwork.data.common.messages.DataMessage;
import org.thethingsnetwork.data.common.messages.UplinkMessage;
import org.thethingsnetwork.data.mqtt.Client;

@RestController
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/")
    public String index() {
        logger.debug("Some debug!");
        logger.info("Some info!");
        logger.error("Some error!");
        return "Heyyy hello from Spring Boot!";
    }
    
    @GetMapping(path = "/connectToTTN")
    public String connectToTTN() throws Exception {
        final String region = "eu";
        final String appId = "ridetrack";
        final String accessKey = "ttn-account-v2.FnXxZ08ZXLja9Uc4yNCsPzdZxlwgrqEAMnLQX3CPgMg";
        Client client = new Client(region, appId, accessKey);
        client.onError((Throwable error) -> logger.error("{}", error.getMessage()));
        client.onConnected((Connection connection) -> logger.info("connected"));
        client.onActivation((String devId, ActivationMessage activationMsg) -> logger.info("Activation: {}, data: {}", devId, activationMsg));
        client.onMessage((String devId, DataMessage data) -> {
            if (data instanceof UplinkMessage) {
                UplinkMessage uplinkMessage = (UplinkMessage) data;
                logger.info("Message: {} {}", devId, Arrays.toString(uplinkMessage.getPayloadRaw()));
            }
        });
        client.start();
        logger.info("connected");
        return "OK";
    }
}
