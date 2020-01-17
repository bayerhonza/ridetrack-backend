package com.ensimag.ridetrack.auth.acl;
/**
 * Permission des ACL
 */
public enum AclPermission {
	/** Permission read */
	READ(1),
	/** Permission write */
	WRITE(1 << 1),
	/** Permission create */
	CREATE(1 << 2),
	/** Permission delete */
	DELETE(1 << 3),
	/** Permissino administrator */
	ADMINISTRATOR(1 << 4);
	
	private int mask;
	
	AclPermission(int mask) {
		this.mask = mask;
	}
	
	/**
	 * Bit mask of privilege
	 * @return bit mask
	 */
	public int getMask() {
		return mask;
	}
	
	/**
	 * Utility for merging permissions
	 * @param aclPermissions permissions
	 * @return logical or of permission mask
	 */
	public static int of(AclPermission... aclPermissions) {
		int resultMask = 0;
		for (AclPermission aclPermission : aclPermissions) {
			resultMask |= aclPermission.getMask();
		}
		return resultMask;
	}
}
