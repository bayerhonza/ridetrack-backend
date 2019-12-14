package com.ensimag.ridetrack.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ensimag.ridetrack.auth.acl.AclService;
import com.ensimag.ridetrack.models.Space;
import com.ensimag.ridetrack.models.SpaceUser;
import com.ensimag.ridetrack.repository.RtUserRepository;
import com.ensimag.ridetrack.roles.RoleManager;
import com.ensimag.ridetrack.roles.RoleType;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@PreAuthorize("hasRole('CLIENT')")
public class SpaceUserManager {
	
	@Autowired
	private RoleManager roleManager;
	
	@Autowired
	private SpaceManager spaceManager;
	
	@Autowired
	private ClientManager clientManager;
	
	@Autowired
	private AclService aclService;
	
	@Autowired
	private RtUserRepository rtUserRepository;
	
	@PreAuthorize("hasPermission(#space,'CAN_CREATE_USER')")
	public void createUser(Space space, SpaceUser user) {
		log.info("Creating user '{}'", user.getUsername());
		rtUserRepository.save(user);
		
		space.addUser(user);
		user.setSpace(space);
		spaceManager.updateSpace(space);
		
		roleManager.assignRoleToUser(RoleType.USER, user);
		rtUserRepository.save(user);
		
	}
}
