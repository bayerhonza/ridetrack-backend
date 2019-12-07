package com.ensimag.ridetrack.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.ensimag.ridetrack.auth.TokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider implements TokenProvider {
	
	private JwtConfiguration jwtConfiguration;
	
	public JwtTokenProvider(@Autowired JwtConfiguration jwtConfiguration) {
		this.jwtConfiguration = jwtConfiguration;
	}
	
	@Override
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims getAllClaimsFromToken(String token) {
		return parseToken(token).getBody();
	}
	
	/**
	 * Parse the JWT token and verify its validity
	 * @param token JWT token
	 * @return container of Jws
	 * @throws io.jsonwebtoken.JwtException if not valid token
	 */
	private Jws<Claims> parseToken(String token) {
		return Jwts.parser()
				.setSigningKey(jwtConfiguration.getSecret())
				.parseClaimsJws(token);
	}
	
	@Override
	@PreAuthorize("isFullyAuthenticated()")
	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, username);
	}
	
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		long timestampNow = System.currentTimeMillis();
		log.info("Generating new token for subject '{}'", subject);
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(timestampNow))
				.setExpiration(new Date(timestampNow + jwtConfiguration.getTokenValidity()))
				.signWith(SignatureAlgorithm.HS512, jwtConfiguration.getSecret()).compact();
	}
	
	@Override
	public void validateToken(String token){
		parseToken(token);
	}
}