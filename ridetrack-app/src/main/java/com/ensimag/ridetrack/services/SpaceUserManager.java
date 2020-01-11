package com.ensimag.ridetrack.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ensimag.ridetrack.auth.acl.AclService;
import com.ensimag.ridetrack.exception.RidetrackNotFoundException;
import com.ensimag.ridetrack.models.Space;
import com.ensimag.ridetrack.models.SpaceUser;
import com.ensimag.ridetrack.repository.SpaceUserRepository;
import com.ensimag.ridetrack.auth.roles.RoleManager;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class SpaceUserManager {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleManager roleManager;
	
	@Autowired
	private SpaceManager spaceManager;
	
	@Autowired
	private ClientManager clientManager;
	
	@Autowired
	private AclService aclService;
	
	@Autowired
	private SpaceUserRepository userRepository;
	
	@PreAuthorize("hasPermission(#space,'CAN_CREATE_USER')")
	public void createUser(Space space, SpaceUser newUser) {
		log.info("Creating user '{}'", newUser.getUsername());
		space.addUser(newUser);
		spaceManager.updateSpace(space);
		roleManager.assignRoleToUser(newUser);
		userRepository.save(newUser);
		
		userRepository.save(newUser);
		
	}
	
	public SpaceUser findSpaceUserOrThrow(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new RidetrackNotFoundException("Usern not found"));
	}
}
