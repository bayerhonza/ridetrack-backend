package com.ensimag.ridetrack.init;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ensimag.ridetrack.models.Privilege;
import com.ensimag.ridetrack.models.Role;
import com.ensimag.ridetrack.models.User;
import com.ensimag.ridetrack.repository.PrivilegeRepository;
import com.ensimag.ridetrack.repository.RoleRepository;
import com.ensimag.ridetrack.repository.UserRepository;

@Component
public class InitLoader implements ApplicationListener<ContextRefreshedEvent> {

	boolean alreadySetup = false;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PrivilegeRepository privilegeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (alreadySetup || userRepository.findByUsername("administrator").isPresent()) {
			return;
		}
		Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
		Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
		
		Set<Privilege> adminPrivileges = Set.of(readPrivilege, writePrivilege);
		Role adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
		createRoleIfNotFound("ROLE_USER",Set.of(readPrivilege));
		
		User user = User.builder()
				.name("Admin")
				.surname("Ridetrack")
				.username("administrator")
				.password(passwordEncoder.encode("toto"))
				.email("jan.bayer@grenole-inpr.org")
				.roles(Set.of(adminRole))
				.enabled(true)
				.build();
		userRepository.save(user);


	}
	
	public Privilege createPrivilegeIfNotFound(String name) {
		
		Optional<Privilege> privilegeOpt = privilegeRepository.findByOperationName(name);
		if (privilegeOpt.isEmpty()) {
			return privilegeRepository.save(Privilege.of(name));
		}
		return privilegeOpt.get();
	}
	
	public Role createRoleIfNotFound(String name, Set<Privilege> privileges) {

		Optional<Role> roleOpt = roleRepository.findByName(name);
		if (roleOpt.isEmpty()) {
			Role role = new Role(name);
			role.setPrivileges(privileges);
			return roleRepository.save(role);
		}
		return roleOpt.get();
	}
}
