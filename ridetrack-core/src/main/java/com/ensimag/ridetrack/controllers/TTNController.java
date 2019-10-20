package com.ensimag.ridetrack.controllers;

import com.ensimag.ridetrack.radio.TTNClient;
import com.ensimag.ridetrack.radio.TTNService;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thethingsnetwork.data.common.Connection;
import org.thethingsnetwork.data.common.messages.ActivationMessage;
import org.thethingsnetwork.data.common.messages.DataMessage;
import org.thethingsnetwork.data.common.messages.UplinkMessage;

@RestController
public class TTNController {

    @Value("${ridetrack.ttn.region}")
    private String ttnRegion;

    @Value("${ridetrack.ttn.appId}")
    private String ttnAppId;

    @Value("${ridetrack.ttn.accessKey}")
    private String ttnAccessKey;

    private static final Logger logger = LoggerFactory.getLogger(TTNController.class);

    @GetMapping("/")
    public String index() {
        logger.debug("Some debug!");
        logger.info("Some info!");
        logger.error("Some error!");
        return "Heyyy hello from Spring Boot!";
    }
    
    @GetMapping(path = "/connectToTTN")
    public String connectToTTN() throws Exception {
        logger.info("Connecting to {}.thethings.network with appId {} and accessKey {}",ttnRegion, ttnAppId, ttnAccessKey);
        TTNClient ttnClient = TTNService.getTTNClient(ttnRegion, ttnAppId, ttnAccessKey);
        ttnClient.onError((Throwable error) -> logger.error("{}", error.getMessage()));
        ttnClient.onConnected((Connection connection) -> logger.info("connected"));
        ttnClient.onActivation((String devId, ActivationMessage activationMsg) -> logger
            .info("Activation: {}, data: {}", devId, activationMsg));
        ttnClient.onMessage((String devId, DataMessage data) -> {
            if (data instanceof UplinkMessage) {
                UplinkMessage uplinkMessage = (UplinkMessage) data;
                logger.info("Message: {} {} metadata: {}", devId, Arrays.toString(uplinkMessage.getPayloadRaw()),uplinkMessage.getMetadata());
            }
        });
        ttnClient.start();
        logger.info("connected");
        return "OK";
    }
}
