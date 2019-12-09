package com.ensimag.ridetrack.configuration;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthenticationHandlerInterceptor extends HandlerInterceptorAdapter implements ApplicationListener<ContextRefreshedEvent> {
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
	
	}
}
