package com.ensimag.ridetrack.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
	
	@Bean
	public JwtConfiguration jwtConfiguration() {
		return new JwtConfiguration();
	}
}
