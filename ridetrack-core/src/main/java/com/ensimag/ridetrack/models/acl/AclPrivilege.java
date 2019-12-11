package com.ensimag.ridetrack.models.acl;
public enum AclPrivilege {
	
	READ(1),
	WRITE(1 << 1),
	CREATE(1 << 2),
	DELETE(1 << 3),
	ADMINISTRATOR(1 << 4);
	
	private int mask;
	
	AclPrivilege(int mask) {
		this.mask = mask;
	}
	
	int getMask() {
		return mask;
	}
	
	public static int of(AclPrivilege ... privileges) {
		int resultMask = 0;
		for (AclPrivilege aclPrivilege : privileges) {
			resultMask |= aclPrivilege.getMask();
		}
		return resultMask;
	}
	
	public static int noPrivilege() {
		return 0;
	}
}
