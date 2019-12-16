package com.ensimag.ridetrack.auth;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import com.ensimag.ridetrack.auth.acl.AclService;
import com.ensimag.ridetrack.models.AdminUser;
import com.ensimag.ridetrack.models.RtUser;
import com.ensimag.ridetrack.models.acl.AclObjectIdentity;
import com.ensimag.ridetrack.models.acl.AclOidUserGroup;
import com.ensimag.ridetrack.models.acl.AclPrivilege;
import com.ensimag.ridetrack.models.acl.AclSid;
import com.ensimag.ridetrack.privileges.PrivilegeEnum;

public class RtPermissionEvaluator implements PermissionEvaluator {
	
	private AclService aclService;
	
	public RtPermissionEvaluator(AclService aclService) {
		this.aclService = aclService;
	}
	
	@Override
	public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {
		if (auth == null) {
			return false;
		}
		RtUserPrincipal principal = (RtUserPrincipal) auth.getPrincipal();
		if (principal.getPrincipalObject() instanceof AdminUser) {
			return true;
		}
		
		if (targetDomainObject == null || !(permission instanceof PrivilegeEnum)) {
			return false;
		}
		PrivilegeEnum privilegeEnum = (PrivilegeEnum) permission;
		AclPrivilege aclPrivilege = aclService.getAclPrivilegeByName(privilegeEnum);
		AclSid aclSid = principal.getPrincipalObject();
		AclObjectIdentity aclOid = (AclObjectIdentity) targetDomainObject;
		
		if (principal.getPrincipalObject() instanceof RtUser) {
			RtUser rtUser = (RtUser) principal.getPrincipalObject();
			Set<AclOidUserGroup> userGroups = rtUser.getUserGroups();
			if (!userGroups.isEmpty()) {
				Optional<AclOidUserGroup> result = userGroups.stream()
						.filter(userGroup -> aclService.isAuthorized(userGroup, aclOid, aclPrivilege))
						.findFirst();
				if (result.isPresent()) {
					return true;
				}
			}
		}
		
		return aclService.isAuthorized(aclSid, aclOid, aclPrivilege);
		
	}
	
	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
		throw new UnsupportedOperationException("hasPermission(authentication, targetId, targetType, permission) not supported");
		
	}
}
