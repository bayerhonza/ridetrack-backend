package com.ensimag.ridetrack.rest.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ensimag.ridetrack.auth.RtUserPrincipal;
import com.ensimag.ridetrack.dto.SpaceUserDTO;
import com.ensimag.ridetrack.mappers.SpaceUserMapper;
import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.models.Space;
import com.ensimag.ridetrack.models.SpaceUser;
import com.ensimag.ridetrack.rest.api.RestPaths;
import com.ensimag.ridetrack.services.ClientManager;
import com.ensimag.ridetrack.services.SpaceManager;
import com.ensimag.ridetrack.services.ClientUserManager;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(RestPaths.API_PATH)
@Slf4j
@PreAuthorize("hasRole('CLIENT')")
public class SpaceUserController {
	
	@Autowired
	private ClientUserManager userManager;
	
	@Autowired
	private SpaceManager spaceManager;
	
	@Autowired
	private SpaceUserMapper spaceUserMapper;
	
	@Autowired
	private ClientManager clientManager;
	
	@PostMapping("/space/{spaceName}/user")
	public SpaceUserDTO createUser(
			@AuthenticationPrincipal RtUserPrincipal principal,
			@PathVariable("spaceName") String spaceName,
			@Valid @RequestBody SpaceUserDTO userDetails) {
		Client client = clientManager.findClientOrThrow(principal.getUsername());
		Space space = spaceManager.findSpaceOfClientOrThrow(client, spaceName);
		SpaceUser newUser = spaceUserMapper.toUser(userDetails);
		userManager.createUser(space, newUser);
		return spaceUserMapper.toUserDTO(newUser);
	}
	
}
