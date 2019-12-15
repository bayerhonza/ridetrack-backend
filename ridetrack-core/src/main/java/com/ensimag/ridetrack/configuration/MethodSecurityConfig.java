package com.ensimag.ridetrack.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import com.ensimag.ridetrack.auth.acl.AclService;
import com.ensimag.ridetrack.auth.CustomPermissionEvaluator;

@EnableGlobalMethodSecurity(
		securedEnabled = true,
		jsr250Enabled = true,
		prePostEnabled = true
)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
	
	@Bean
	public MethodSecurityExpressionHandler
	defaultMethodSecurityExpressionHandler(@Autowired AclService aclService) {
		DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
		expressionHandler.setPermissionEvaluator(new CustomPermissionEvaluator(aclService));
		return expressionHandler;
	}
}
