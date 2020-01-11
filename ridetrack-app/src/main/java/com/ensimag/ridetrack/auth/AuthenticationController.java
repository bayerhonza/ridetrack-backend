package com.ensimag.ridetrack.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ensimag.ridetrack.auth.jwt.AuthRequest;
import com.ensimag.ridetrack.auth.jwt.JwtResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	
	private final TokenProvider tokenProvider;
	
	public AuthenticationController(
			@Autowired AuthenticationService authenticationService,
			@Autowired TokenProvider tokenProvider) {
		this.authenticationService = authenticationService;
		this.tokenProvider = tokenProvider;
	}
	
	@PostMapping(value = "/authenticate")
	public ResponseEntity<JwtResponse> createAuthenticationToken(
			@RequestBody AuthRequest authenticationRequest) {
		authenticationService.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final String token = tokenProvider.generateToken(authenticationRequest.getUsername());
		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	@GetMapping("/api/whoami")
	@ResponseBody
	@PreAuthorize("isFullyAuthenticated()")
	public String currentUserName(@AuthenticationPrincipal UserDetails user) {
		return user.getUsername();
		
	}

	
}
