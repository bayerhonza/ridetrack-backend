package com.ensimag.ridetrack.privileges;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.ensimag.ridetrack.models.acl.AclPrivilege;

public interface PrivilegeManager {
	
	void init();
	
	AclPrivilege createPrivilegeIfNotFound(PrivilegeEnum privilege);
	
	AclPrivilege getPrivilege(PrivilegeEnum privilegeEnum);
}
