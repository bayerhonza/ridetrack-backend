package com.ensimag.ridetrack.auth.roles;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ensimag.ridetrack.models.ClientUser;
import com.ensimag.ridetrack.models.Role;
import com.ensimag.ridetrack.models.SpaceUser;
import lombok.extern.slf4j.Slf4j;

/**
 * Manager of roles
 */
@Service
@Transactional
@Slf4j
public class RoleManager {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	public void init() {
		for (RoleType roleType : RoleType.values()) {
			createRoleIfNotFound(roleType.getRoleName());
		}
	}
	
	/**
	 * Assigns user role to space user
	 * @param user space user
	 */
	public void assignRoleToUser(SpaceUser user) {
		Role entityRole = createRoleIfNotFound(RoleType.USER.getRoleName());
		user.addRole(entityRole);
	}
	
	/**
	 * Assigns client role to client user
	 * @param user client user
	 */
	public void assignRoleToClientUser(ClientUser user) {
		Role entityRole = createRoleIfNotFound(RoleType.CLIENT.getRoleName());
		user.addRole(entityRole);
	}
	
	private Role createRoleIfNotFound(String name) {
		Optional<Role> roleOpt = roleRepository.findByName(name);
		if (roleOpt.isEmpty()) {
			Role role = new Role(name);
			return roleRepository.save(role);
		}
		return roleOpt.get();
	}
}
