package com.ensimag.ridetrack.rest.controllers;

import com.ensimag.ridetrack.auth.AuthenticationService;
import com.ensimag.ridetrack.dto.SpaceDef;
import com.ensimag.ridetrack.services.SpaceManager;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/space")
@Slf4j
public class SpaceController {

	private final SpaceManager spaceManager;


	private final AuthenticationService authenticationService;

	public SpaceController(SpaceManager spaceManager,
		AuthenticationService authenticationService) {
		this.spaceManager = spaceManager;
		this.authenticationService = authenticationService;
	}

	@PostMapping(path = "/")
	public ResponseEntity<Object> createSpace(@Valid @RequestBody SpaceDef spaceDef) {
		return new ResponseEntity<>(HttpStatus.OK);
	}


}
