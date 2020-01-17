package com.ensimag.ridetrack.auth.jwt;

import java.io.Serializable;

import lombok.Getter;

/**
 * POJO for authentication request
 */
@Getter
public class AuthRequest implements Serializable {
	
	private String username;
	private String password;
	
	
	public AuthRequest() {
	}
	
	public AuthRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
