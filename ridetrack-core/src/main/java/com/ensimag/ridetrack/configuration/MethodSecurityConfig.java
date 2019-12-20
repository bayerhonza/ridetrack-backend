package com.ensimag.ridetrack.configuration;

import static com.ensimag.ridetrack.roles.RoleType.ADMIN;
import static com.ensimag.ridetrack.roles.RoleType.CLIENT;
import static com.ensimag.ridetrack.roles.RoleType.GUEST;
import static com.ensimag.ridetrack.roles.RoleType.USER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import com.ensimag.ridetrack.auth.RtPermissionEvaluator;
import com.ensimag.ridetrack.auth.acl.AclService;
import lombok.extern.slf4j.Slf4j;

@EnableGlobalMethodSecurity(
		securedEnabled = true,
		jsr250Enabled = true,
		prePostEnabled = true
)
@Slf4j
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
	
	@Bean
	public MethodSecurityExpressionHandler
	defaultMethodSecurityExpressionHandler(@Autowired AclService aclService) {
		DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
		expressionHandler.setPermissionEvaluator(new RtPermissionEvaluator(aclService));
		expressionHandler.setRoleHierarchy(roleHierarchy());
		return expressionHandler;
	}
	
	@Bean
	public RoleHierarchy roleHierarchy() {
		RoleHierarchyImpl r = new RoleHierarchyImpl();
		String roleHierarchySettings = ADMIN.getRoleName() + " > " + CLIENT.getRoleName() + "\n"
				+ CLIENT.getRoleName() + " >  " + USER.getRoleName() + "\n"
				+ USER.getRoleName() + " > " + GUEST.getRoleName();
		log.debug("Role hierarchy: {}", roleHierarchySettings.replace('\n',','));
		r.setHierarchy(roleHierarchySettings);
		return r;
	}
}
