package com.ensimag.ridetrack;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thethingsnetwork.data.common.Connection;
import org.thethingsnetwork.data.common.messages.ActivationMessage;
import org.thethingsnetwork.data.common.messages.DataMessage;
import org.thethingsnetwork.data.common.messages.UplinkMessage;
import org.thethingsnetwork.data.mqtt.Client;

import java.net.URISyntaxException;
import java.util.Arrays;

@RestController
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping("/")
    public String index() {
        logger.debug("Some debug!");
        logger.info("Some info!");
        logger.error("Some error!");
        return "Heyyy hello from Spring Boot!";
    }

    @RequestMapping("/connectToTTN")
    public String connectToTTN() throws Exception {
        final String region = "eu";
        final String appId = "ridetrack";
        final String accessKey = "ttn-account-v2.FnXxZ08ZXLja9Uc4yNCsPzdZxlwgrqEAMnLQX3CPgMg";
        Client client = new Client(region, appId, accessKey);
        client.onError((Throwable _error) -> logger.error("{}", _error.getMessage()));
        client.onConnected((Connection _client) -> logger.info("connected"));
        client.onActivation((String _devId, ActivationMessage _activationMsg) -> logger.info("Activation: {}, data: {}",_devId, _activationMsg));
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
