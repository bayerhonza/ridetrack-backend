package com.ensimag.ridetrack.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ensimag.ridetrack.exception.RestException;
import com.ensimag.ridetrack.jwt.JwtRequest;
import com.ensimag.ridetrack.jwt.JwtResponse;
import com.ensimag.ridetrack.jwt.JwtTokenUtil;

@RestController
@CrossOrigin
public class AuthenticationController {

	private final AuthenticationManager authenticationManager;

	private final UserDetailsService userDetailsService;

	private final JwtTokenUtil jwtTokenUtil;

	public AuthenticationController(
		@Autowired AuthenticationManager authenticationManager,
		@Autowired JwtTokenUtil jwtTokenUtil,
		@Autowired UserDetailsService userDetailsService) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
		this.userDetailsService = userDetailsService;
	}

	@PostMapping(value = "/authenticate")
	public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService
			.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	@GetMapping("/api/username")
	@ResponseBody
	public String currentUserName(@AuthenticationPrincipal User user) {
		return user.getUsername() + " " + user.getPassword();
	}

	private void authenticate(String username, String password) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new RestException(HttpStatus.UNAUTHORIZED, "User disabled");
		} catch (BadCredentialsException e) {
			throw new RestException(HttpStatus.UNAUTHORIZED,"Invalid credetials");
		}
	}
}
