package com.ensimag.ridetrack;

import java.util.Arrays;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class BaseApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class, args);
    }
    
    @Bean
    public CommandLineRunner commandLineRunnerSecond(ApplicationContext ctx) {
        return args -> log.info("args {}", Arrays.asList(args));
    }
    
    @Bean
    public ApplicationRunner applicationRunner(ApplicationContext ctx) {
        return args -> log.info("application {} with id {} started at {}", ctx.getApplicationName(), ctx.getId(), new Date(ctx.getStartupDate()));
    }
}
