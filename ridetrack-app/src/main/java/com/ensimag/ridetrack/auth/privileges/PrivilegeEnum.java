package com.ensimag.ridetrack.auth.privileges;
/**
 * Enum of possible privileges
 */
public enum PrivilegeEnum {
	CAN_CREATE_SPACE("CAN_CREATE_SPACE"),
	CAN_CREATE_SPACE_USER("CAN_CREATE_SPACE_USER"),
	CAN_CREATE_DEV_GROUP("CAN_CREATE_DEV_GROUP"),
	CAN_CREATE_DEVICE("CAN_CREATE_DEVICE"),
	CAN_DELETE("CAN_DELETE"),
	CAN_DELETE_SPACES("CAN_DELETE_SPACES"),
	CAN_UPDATE("CAN_UPDATE"),
	CAN_READ("CAN_READ");
	
	PrivilegeEnum(String name) {
		this.name = name;
	}
	
	private String name;
	
	public String getName() {
		return this.name;
	}
}
