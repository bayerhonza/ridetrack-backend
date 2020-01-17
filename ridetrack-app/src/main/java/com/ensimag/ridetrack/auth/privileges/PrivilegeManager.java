package com.ensimag.ridetrack.auth.privileges;

import com.ensimag.ridetrack.models.acl.AclPrivilege;

/**
 * Manager of privilege
 */
public interface PrivilegeManager {
	
	/**
	 * Creates a privilege and its entity
	 * @param privilege enum of privileges
	 * @return entity of privilege
	 */
	AclPrivilege createPrivilegeIfNotFound(PrivilegeEnum privilege);
	
	AclPrivilege getPrivilege(PrivilegeEnum privilegeEnum);
}
