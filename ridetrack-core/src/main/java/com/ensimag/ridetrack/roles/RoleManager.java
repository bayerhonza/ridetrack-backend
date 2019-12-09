package com.ensimag.ridetrack.roles;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ensimag.ridetrack.models.Privilege;
import com.ensimag.ridetrack.models.Role;
import com.ensimag.ridetrack.models.SpaceUser;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class RoleManager {
	
	private Map<RoleType, Role> roleToEntityMap;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	public void init() {
		for (RoleType roleType : RoleType.values()) {
			Role role = createRoleIfNotFound(roleType.getRoleName(), new HashSet<>());
			roleToEntityMap.put(roleType, role);
		}
	}
	
	public void assignRoleToUser(RoleType roleType, SpaceUser user) {
		Role entityRole = roleToEntityMap.get(roleType);
		user.addRole(entityRole);
	}
	
	private Role createRoleIfNotFound(String name, Set<Privilege> privileges) {
		
		Optional<Role> roleOpt = roleRepository.findByName(name);
		if (roleOpt.isEmpty()) {
			Role role = new Role(name);
			role.setPrivileges(privileges);
			return roleRepository.save(role);
		}
		return roleOpt.get();
	}
}
