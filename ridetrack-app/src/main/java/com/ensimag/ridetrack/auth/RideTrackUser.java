package com.ensimag.ridetrack.auth;


public class RideTrackUser {

	private String username;
	private String hashPassword;
	private String sessionId;


	public String getSessionId() {
		return this.sessionId;
	}

	public String getUsername() {
		return this.username;
	}

	public String getHashPassword() {
		return this.hashPassword;
	}
}
