package com.ensimag.ridetrack.rest.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ensimag.ridetrack.rest.api.RestPaths;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(RestPaths.API_PATH)
public class TestController {

    @GetMapping("/test")
    @PreAuthorize("isAuthenticated()")
    public String test() {
        log.debug("Some debug!");
        log.info("Some info!");
        log.error("Some error!");
        return "Heyyy hello from Spring Boot!";
    }
}
