package com.ensimag.ridetrack.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ensimag.ridetrack.exception.RestException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthenticationService {
	
	
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationService(
			@Autowired AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	public void authenticate(String username, String password) {
		try {
			log.info("Authenticating user=[{}] using username-password.", username);
			
			log.debug("Before authentification: {}", SecurityContextHolder.getContext().getAuthentication());
			
			Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			SecurityContextHolder.getContext().setAuthentication(auth);
			log.debug("After authentification: {}", SecurityContextHolder.getContext().getAuthentication());
			
		} catch (DisabledException e) {
			throw new RestException(HttpStatus.UNAUTHORIZED, "User disabled");
		} catch (BadCredentialsException e) {
			throw new RestException(HttpStatus.UNAUTHORIZED,"Invalid credetials");
		}
	}
}
