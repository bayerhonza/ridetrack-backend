package com.ensimag.ridetrack.init;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ensimag.ridetrack.models.AdminUser;
import com.ensimag.ridetrack.models.Role;
import com.ensimag.ridetrack.models.acl.AclPrivilege;
import com.ensimag.ridetrack.repository.acl.AclPrivilegeRepository;
import com.ensimag.ridetrack.repository.RtUserRepository;
import com.ensimag.ridetrack.roles.RoleRepository;
import com.ensimag.ridetrack.roles.RoleType;

@Component
public class InitLoader implements ApplicationListener<ContextRefreshedEvent> {

	boolean alreadySetup = false;
	
	@Value("#{'${ridetrack.auth.defaultProperties}'.split(',')}")
	private List<String> defaultPrivileges;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private AclPrivilegeRepository aclPrivilegeRepository;
	
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
		
		Role adminRole = createRoleIfNotFound(RoleType.ADMIN);
		
		AdminUser adminUser = new AdminUser("administrator", passwordEncoder.encode("toto"));
		adminUser.addRole(adminRole);
		rtUserRepository.save(adminUser);
		defaultPrivileges.forEach(this::createPrivilegeIfNotFound);
	}
	
	public Role createRoleIfNotFound(RoleType roleType) {
		Optional<Role> roleOpt = roleRepository.findByName(roleType.getRoleName());
		if (roleOpt.isEmpty()) {
			return roleRepository.save(new Role(roleType.getRoleName()));
		}
		return roleOpt.get();
	}
	
	public void createPrivilegeIfNotFound(String privilege) {
		Optional<AclPrivilege> privilegeOpt = aclPrivilegeRepository.findByPrivilegeName(privilege);
		if (privilegeOpt.isEmpty()) {
			aclPrivilegeRepository.save(new AclPrivilege(privilege));
		}
	}
}
