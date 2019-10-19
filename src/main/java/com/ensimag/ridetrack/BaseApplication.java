package com.ensimag.ridetrack;

import java.util.Arrays;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BaseApplication {
    
    private static final Logger logger = LoggerFactory.getLogger(BaseApplication.class);
    
    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class, args);
    }
    
    @Bean
    public CommandLineRunner commandLineRunnerSecond(ApplicationContext ctx) {
        return args -> logger.info("args {}", Arrays.asList(args));
    }
    
    @Bean
    public ApplicationRunner applicationRunner(ApplicationContext ctx) {
        return args -> logger.info("application {} with id {} started at {}", ctx.getApplicationName(), ctx.getId(), new Date(ctx.getStartupDate()));
    }
}
