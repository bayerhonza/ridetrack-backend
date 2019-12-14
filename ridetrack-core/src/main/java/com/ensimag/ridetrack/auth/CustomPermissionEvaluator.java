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
		if (targetDomainObject == null || !(permission instanceof AclPrivilege)) {
			return false;
		}
		Optional<AclPrivilege> aclPrivilege = aclService.getAclPrivilegeByName(permission.toString().toUpperCase());
		if (aclPrivilege.isEmpty()) {
			return false;
		}
		
		AclSid aclSid = principal.getPrincipalObject();
		AclObjectIdentity aclOid = (AclObjectIdentity) targetDomainObject;
		
		return aclService.isAuthorized(aclSid, aclOid, aclPrivilege.get());
		
	}
	
	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
		throw new UnsupportedOperationException("hasPermission(authentication, targetId, targetType, permission) not supported");
		
	}
}
