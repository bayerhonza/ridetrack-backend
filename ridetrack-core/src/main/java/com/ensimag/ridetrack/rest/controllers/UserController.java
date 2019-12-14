package com.ensimag.ridetrack.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import com.ensimag.ridetrack.auth.acl.AclService;
import com.ensimag.ridetrack.dto.SpaceUserDTO;
import com.ensimag.ridetrack.exception.RidetrackInternalError;
import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.models.ClientUser;
import com.ensimag.ridetrack.models.acl.AclOidUserGroup;
import com.ensimag.ridetrack.repository.AclOidUserGroupRepository;
import com.ensimag.ridetrack.repository.RtUserRepository;
import com.ensimag.ridetrack.rest.api.RestPaths;
import com.ensimag.ridetrack.roles.RoleManager;
import com.ensimag.ridetrack.services.ClientManager;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(RestPaths.API_PATH)
@Slf4j
@Transactional
public class UserController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ClientManager clientManager;
	
	@Autowired
	private RoleManager roleManager;
	
	@Autowired
	private RtUserRepository userRepository;
	
	@Autowired
	private AclOidUserGroupRepository userGroupRepository;
	
	@Autowired
	private AclService aclService;
	
	@PostMapping("/{clientName}/user")
	public EntityResponse<SpaceUserDTO> createClientUser(
			@PathVariable(name = "clientName") String clientName,
			@RequestParam String username,
			@RequestParam String password) {
		Client client = clientManager.findClientOrThrow(clientName);
		ClientUser newClientUser = new ClientUser().toBuilder()
				.username(username)
				.password(passwordEncoder.encode(password))
				.assignedClient(client)
				.enabled(true)
				.build();
		userRepository.save(newClientUser);
		roleManager.assignRoleToClientUser(newClientUser);
		userRepository.save(newClientUser);
		AclOidUserGroup clientUserGroup = userGroupRepository.findByName(clientManager.getDefaultClientUserGroupName(client))
				.orElseThrow(() -> new RidetrackInternalError("Client user group not found"));
		clientUserGroup.addUser(newClientUser);
		userGroupRepository.save(clientUserGroup);
		return null;
	}
	
	
	
}
