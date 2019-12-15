package com.ensimag.ridetrack.auth;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.web.client.HttpServerErrorException.NotImplemented;

import java.io.Serializable;
import java.util.Optional;

import javax.naming.OperationNotSupportedException;
import javax.transaction.NotSupportedException;

import com.ensimag.ridetrack.auth.acl.AclService;
import com.ensimag.ridetrack.models.AdminUser;
import com.ensimag.ridetrack.models.RtUser;
import com.ensimag.ridetrack.models.acl.AclObjectIdentity;
import com.ensimag.ridetrack.models.acl.AclPrivilege;
import com.ensimag.ridetrack.models.acl.AclSid;
import com.ensimag.ridetrack.privileges.PrivilegeEnum;

public class CustomPermissionEvaluator implements PermissionEvaluator {
	
	private AclService aclService;
	
	public CustomPermissionEvaluator(AclService aclService) {
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
		if (principal.getPrincipalObject() instanceof RtUser) {
			RtUser rtUser = (RtUser) principal.getPrincipalObject();
			
		}
		if (targetDomainObject == null || !(permission instanceof AclPrivilege)) {
			return false;
		}
		PrivilegeEnum privilegeEnum;
		try {
			 privilegeEnum = PrivilegeEnum.valueOf(permission.toString());
		} catch (IllegalArgumentException ex) {
			return false;
		}
		AclPrivilege aclPrivilege = aclService.getAclPrivilegeByName(privilegeEnum);
		
		
		AclSid aclSid = principal.getPrincipalObject();
		AclObjectIdentity aclOid = (AclObjectIdentity) targetDomainObject;
		
		return aclService.isAuthorized(aclSid, aclOid, aclPrivilege);
		
	}
	
	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
		throw new UnsupportedOperationException("hasPermission(authentication, targetId, targetType, permission) not supported");
		
	}
}
