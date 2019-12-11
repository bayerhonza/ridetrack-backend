package com.ensimag.ridetrack.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ensimag.ridetrack.models.Space;
import com.ensimag.ridetrack.models.SpaceUser;
import com.ensimag.ridetrack.repository.PrivilegeRepository;
import com.ensimag.ridetrack.repository.RtUserRepository;
import com.ensimag.ridetrack.roles.RoleManager;
import com.ensimag.ridetrack.roles.RoleType;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class SpaceUserManager {
	
	@Autowired
	private PrivilegeRepository privilegeRepository;
	
	@Autowired
	private RoleManager roleManager;
	
	@Autowired
	private SpaceManager spaceManager;
	
	@Autowired
	private ClientManager clientManager;
	
	@Autowired
	private RtUserRepository rtUserRepository;
	
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
