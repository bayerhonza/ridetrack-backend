package com.ensimag.ridetrack.auth.jwt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

/**
 * POJO of JWT configuration. Definition of token validty and secrete to generate/parse JWT
 */
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
