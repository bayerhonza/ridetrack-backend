package com.ensimag.ridetrack.auth;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import com.ensimag.ridetrack.auth.acl.AclService;
import com.ensimag.ridetrack.models.AdminUser;
import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.models.ClientUser;
import com.ensimag.ridetrack.models.SpaceUser;
import com.ensimag.ridetrack.models.acl.AclObjectIdentity;
import com.ensimag.ridetrack.models.acl.AclPrivilege;
import com.ensimag.ridetrack.privileges.PrivilegeEnum;

public class RtPermissionEvaluator implements PermissionEvaluator {
	
	private AclService aclService;
	
	public RtPermissionEvaluator(AclService aclService) {
		this.aclService = aclService;
	}
	
	@Override
	public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {
		if (auth == null || targetDomainObject == null || !(permission instanceof PrivilegeEnum)) {
			return false;
		}
		
		RtUserPrincipal principal = (RtUserPrincipal) auth.getPrincipal();
		if (principal.getPrincipalObject() instanceof AdminUser) {
			return true;
		}
		
		AclPrivilege aclPrivilege = aclService.getAclPrivilegeByName((PrivilegeEnum) permission);
		AclObjectIdentity aclOid = (AclObjectIdentity) targetDomainObject;
		
		if (principal.getPrincipalObject() instanceof ClientUser) {
			ClientUser clientUser = (ClientUser) principal.getPrincipalObject();
			Client clientEntity = aclOid.getClient();
			return aclService.evaluateSid(clientUser, clientEntity, aclPrivilege);
		} else if (principal.getPrincipalObject() instanceof SpaceUser) {
			SpaceUser spaceUser = (SpaceUser) principal.getPrincipalObject();
			return aclService.evaluateSid(spaceUser, aclOid, aclPrivilege);
		}
		return false;
		
	}
	
	
	
	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
		throw new UnsupportedOperationException("hasPermission(authentication, targetId, targetType, permission) not supported");
		
	}
}
