package com.ensimag.ridetrack.roles;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ensimag.ridetrack.models.ClientUser;
import com.ensimag.ridetrack.models.acl.AclPrivilege;
import com.ensimag.ridetrack.models.Role;
import com.ensimag.ridetrack.models.SpaceUser;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class RoleManager {
	
	private Map<RoleType, Role> roleToEntityMap = new HashMap<>();
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	public void init() {
		for (RoleType roleType : RoleType.values()) {
			Role role = createRoleIfNotFound(roleType.getRoleName());
			roleToEntityMap.put(roleType, role);
		}
	}
	
	public void assignRoleToUser(RoleType roleType, SpaceUser user) {
		Role entityRole = roleToEntityMap.get(roleType);
		user.addRole(entityRole);
	}
	
	public void assignRoleToClientUser(ClientUser user) {
		Role entityRole = roleToEntityMap.get(RoleType.CLIENT);
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
