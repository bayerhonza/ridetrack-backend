package com.ensimag.ridetrack.rest.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import com.ensimag.ridetrack.auth.RtUserPrincipal;
import com.ensimag.ridetrack.auth.acl.AclService;
import com.ensimag.ridetrack.dto.SpaceUserDTO;
import com.ensimag.ridetrack.exception.RidetrackInternalError;
import com.ensimag.ridetrack.mappers.SpaceUserMapper;
import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.models.Space;
import com.ensimag.ridetrack.models.SpaceUser;
import com.ensimag.ridetrack.models.acl.AclOidUserGroup;
import com.ensimag.ridetrack.repository.AclOidUserGroupRepository;
import com.ensimag.ridetrack.repository.RtUserRepository;
import com.ensimag.ridetrack.rest.api.RestPaths;
import com.ensimag.ridetrack.services.ClientManager;
import com.ensimag.ridetrack.services.SpaceManager;
import com.ensimag.ridetrack.services.SpaceUserManager;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(RestPaths.API_PATH)
@Slf4j
@PreAuthorize("hasRole('CLIENT')")
public class SpaceUserController {
	
	@Autowired
	private SpaceUserManager userManager;
	
	@Autowired
	private SpaceManager spaceManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ClientManager clientManager;
	
	@Autowired
	private AclOidUserGroupRepository userGroupRepository;
	
	@Autowired
	private AclService aclService;
	
	@PostMapping("/{clientName}/space/{spaceName}/user")
	public EntityResponse<SpaceUserDTO> createSpaceUser(
			@PathVariable(name = "clientName") String clientName,
			@PathVariable(name = "spaceName") String spaceName,
			@RequestParam String username,
			@RequestParam String password) {
		Client client = clientManager.findClientOrThrow(clientName);
		Space space = spaceManager.findSpaceOfClientOrThrow(client, spaceName);
		createSpaceUser(space, username, password);
		return null;
	}
	
	@PreAuthorize("hasPermission(#space, T(com.ensimag.ridetrack.privileges.PrivilegeEnum).CAN_CREATE_SPACE_USER)")
	private void createSpaceUser(Space space, String username, String password) {
		SpaceUser spaceUser = new SpaceUser().toBuilder()
				.username(username)
				.password(passwordEncoder.encode(password))
				.space(space)
				.enabled(true)
				.build();
		userManager.createUser(space, spaceUser);
		AclOidUserGroup clientUserGroup = userGroupRepository.findByName(clientManager.getDefaultClientUserGroupName(space.getOwner()))
				.orElseThrow(() -> new RidetrackInternalError("Default space user group not found"));
		AclOidUserGroup spaceUserGroup = userGroupRepository.findByName(spaceManager.getSpaceDefaultUGroupName(space))
				.orElseThrow(() -> new RidetrackInternalError("Default space user group not found"));
		spaceUserGroup.addUser(spaceUser);
		clientUserGroup.addUser(spaceUser);
		
		userGroupRepository.save(spaceUserGroup);
	}
	
}
