package com.ensimag.ridetrack.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import com.ensimag.ridetrack.auth.RtUserPrincipal;
import com.ensimag.ridetrack.auth.acl.AclService;
import com.ensimag.ridetrack.dto.ClientDTO;
import com.ensimag.ridetrack.dto.SpaceUserDTO;
import com.ensimag.ridetrack.exception.RidetrackInternalError;
import com.ensimag.ridetrack.exception.RidetrackNotFoundException;
import com.ensimag.ridetrack.mappers.ClientMapper;
import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.models.ClientUser;
import com.ensimag.ridetrack.models.acl.AclUserGroup;
import com.ensimag.ridetrack.repository.AclUserGroupRepository;
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
	private AclUserGroupRepository userGroupRepository;
	
	@Autowired
	private ClientMapper clientMapper;
	
	@Autowired
	private AclService aclService;
	
	@PostMapping("/{clientName}/user")
	public EntityResponse<SpaceUserDTO> createClientUser(
			@PathVariable(name = "clientName") String clientName,
			@RequestParam String username,
			@RequestParam String password) {
		Client client = clientManager.findClient(clientName);
		ClientUser newClientUser = new ClientUser().toBuilder()
				.username(username)
				.password(passwordEncoder.encode(password))
				.assignedClient(client)
				.enabled(true)
				.build();
		roleManager.assignRoleToClientUser(newClientUser);
		AclUserGroup clientUserGroup = userGroupRepository.findByName(clientManager.getDefaultClientUserGroupName(client))
				.orElseThrow(() -> new RidetrackInternalError("Client user group not found"));
		clientUserGroup.addUser(newClientUser);
		userRepository.save(newClientUser);
		userGroupRepository.save(clientUserGroup);
		return null;
	}
	
	@GetMapping("/myclient")
	public ResponseEntity<ClientDTO> getMyClient(@AuthenticationPrincipal RtUserPrincipal principal) {
		if (principal.getPrincipalObject() instanceof ClientUser) {
			ClientUser clientUser = (ClientUser) principal.getPrincipalObject();
			return ResponseEntity.ok(clientMapper.toClientDTO(clientUser.getAssignedClient()));
		}
		throw new RidetrackNotFoundException("Client of user not found");
	}
	
	
	
}
