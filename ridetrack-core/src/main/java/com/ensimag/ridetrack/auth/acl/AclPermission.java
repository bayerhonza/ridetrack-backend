package com.ensimag.ridetrack.auth.acl;
public enum AclPermission {
	
	READ(1),
	WRITE(1 << 1),
	CREATE(1 << 2),
	DELETE(1 << 3),
	ADMINISTRATOR(1 << 4);
	
	private int mask;
	
	AclPermission(int mask) {
		this.mask = mask;
	}
	
	public int getMask() {
		return mask;
	}
	
	public static int of(AclPermission... aclPermissions) {
		int resultMask = 0;
		for (AclPermission aclPermission : aclPermissions) {
			resultMask |= aclPermission.getMask();
		}
		return resultMask;
	}
	
	public static int noPrivilege() {
		return 0;
	}
}
