package com.ensimag.ridetrack.auth.roles;
/**
 * Types of role
 */
public enum RoleType {
	
	/** Role of admin */
	ADMIN("ROLE_ADMIN"),
	/** Role of client */
	CLIENT("ROLE_CLIENT"),
	/** Role of user */
	USER("ROLE_USER"),
	/* Role of guest */
	GUEST("ROLE_GUEST");
	
	private String name;
	
	RoleType(String name) {
		this.name = name;
	}
	
	public String getRoleName() {
		return name;
	}
}
