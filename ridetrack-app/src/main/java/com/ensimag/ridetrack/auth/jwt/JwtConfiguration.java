package com.ensimag.ridetrack.auth.jwt;
import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtConfiguration {

	private long tokenValidity;

	private String secret;

	public JwtConfiguration(
			@Value("#{ ${spring.security.authentication.jwt.validity} * 1000 }") Long tokenValidity,
			@Value("${spring.security.authentication.jwt.secret}") String secret) {
		this.tokenValidity = tokenValidity;
		this.secret = secret;
	}

}
