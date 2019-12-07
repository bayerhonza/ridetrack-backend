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

import com.ensimag.ridetrack.jwt.AuthRequest;
import com.ensimag.ridetrack.jwt.JwtResponse;
import com.ensimag.ridetrack.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	
	private final JwtTokenProvider jwtTokenProvider;
	
	public AuthenticationController(
			@Autowired AuthenticationService authenticationService,
			@Autowired JwtTokenProvider jwtTokenProvider) {
		this.authenticationService = authenticationService;
		this.jwtTokenProvider = jwtTokenProvider;
	}
	
	@PostMapping(value = "/authenticate")
	public ResponseEntity<JwtResponse> createAuthenticationToken(
			@RequestBody AuthRequest authenticationRequest) {
		authenticationService.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final String token = jwtTokenProvider.generateToken(authenticationRequest.getUsername());
		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	@GetMapping("/api/whoami")
	@ResponseBody
	@PreAuthorize("isFullyAuthenticated()")
	public String currentUserName(@AuthenticationPrincipal UserDetails user) {
		return user.getUsername();
		
	}

	
}
