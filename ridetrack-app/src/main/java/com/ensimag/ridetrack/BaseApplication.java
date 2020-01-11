package com.ensimag.ridetrack;

import java.util.Date;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class BaseApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class);
    }

    
    @Bean
    public ApplicationRunner applicationRunner(ApplicationContext ctx) {
        PacketService packetService = ctx.getBean(PacketService.class);
        packetService.startPacketExecutors();
        return args -> log.info("application {} with id {} started at {}", ctx.getApplicationName(), ctx.getId(), new Date(ctx.getStartupDate()));
    }
}
