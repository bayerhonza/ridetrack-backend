package com.ensimag.ridetrack.auth.privileges;

import com.ensimag.ridetrack.models.acl.AclPrivilege;

public interface PrivilegeManager {
	
	AclPrivilege createPrivilegeIfNotFound(PrivilegeEnum privilege);
	
	AclPrivilege getPrivilege(PrivilegeEnum privilegeEnum);
}
