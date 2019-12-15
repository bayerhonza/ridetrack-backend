package com.ensimag.ridetrack.roles;
public enum RoleType {
	
	ADMIN("ROLE_ADMIN"),
	CLIENT("ROLE_CLIENT"),
	USER("ROLE_USER"),
	GUEST("ROLE_GUEST");
	
	private String name;
	
	RoleType(String name) {
		this.name = name;
	}
	
	public String getRoleName() {
		return name;
	}
}
