package com.ensimag.ridetrack.jwt;
import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;

@Getter
public class JwtConfiguration {
	
	@Value("#{ ${spring.security.authentication.jwt.validity} * 1000 }")
	private long tokenValidity;
	
	@Value("${spring.security.authentication.jwt.secret}")
	private String secret;
	
	
}
