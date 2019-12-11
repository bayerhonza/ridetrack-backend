package com.ensimag.ridetrack.init;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ensimag.ridetrack.models.AdminUser;
import com.ensimag.ridetrack.models.Role;
import com.ensimag.ridetrack.repository.PrivilegeRepository;
import com.ensimag.ridetrack.roles.RoleRepository;
import com.ensimag.ridetrack.repository.RtUserRepository;

@Component
public class InitLoader implements ApplicationListener<ContextRefreshedEvent> {

	boolean alreadySetup = false;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PrivilegeRepository privilegeRepository;
	
	@Autowired
	private RtUserRepository rtUserRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (alreadySetup || rtUserRepository.findByUsername("administrator").isPresent()) {
			return;
		}
		
		Role adminRole = createRoleIfNotFound("ROLE_ADMIN");
		
		AdminUser adminUser = new AdminUser("administrator", passwordEncoder.encode("toto"));
		adminUser.addRole(adminRole);
		rtUserRepository.save(adminUser);
	}
	
	public Role createRoleIfNotFound(String name) {
		Optional<Role> roleOpt = roleRepository.findByName(name);
		if (roleOpt.isEmpty()) {
			return roleRepository.save(new Role(name));
		}
		return roleOpt.get();
	}
}
