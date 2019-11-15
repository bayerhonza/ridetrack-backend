package com.ensimag.ridetrack.rest.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @GetMapping("/test")
    public String test() {
        log.debug("Some debug!");
        log.info("Some info!");
        log.error("Some error!");
        return "Heyyy hello from Spring Boot!";
    }
    
    /*@GetMapping(path = "/connectToTTN")
    public String connectToTTN() throws Exception {
        log.info("Connecting to {}.thethings.network with appId {} and accessKey {}",ttnRegion, ttnAppId, ttnAccessKey);
        TTNClient ttnClient = TTNService.getTTNClient(ttnRegion, ttnAppId, ttnAccessKey);
        ttnClient.onError((Throwable error) -> log.error("{}", error.getMessage()));
        ttnClient.onConnected((Connection connection) -> log.info("connected"));
        ttnClient.onActivation((String devId, ActivationMessage activationMsg) -> log
            .info("Activation: {}, data: {}", devId, activationMsg));
        ttnClient.onMessage((String devId, DataMessage data) -> {
            if (data instanceof UplinkMessage) {
                UplinkMessage uplinkMessage = (UplinkMessage) data;
                log.info("Message: {} {} metadata: {}", devId, Arrays.toString(uplinkMessage.getPayloadRaw()),uplinkMessage.getMetadata());
            }
        });
        ttnClient.start();
        log.info("connected");
        return "OK";
    }*/
}
