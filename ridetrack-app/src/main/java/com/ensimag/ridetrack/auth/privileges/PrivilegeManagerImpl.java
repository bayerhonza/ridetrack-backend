package com.ensimag.ridetrack.auth.privileges;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ensimag.ridetrack.models.acl.AclPrivilege;
import com.ensimag.ridetrack.repository.acl.AclPrivilegeRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Transactional
public class PrivilegeManagerImpl implements PrivilegeManager {
	
	@Autowired
	private AclPrivilegeRepository privilegeRepository;
	
	@Autowired
	public void init() {
		log.info("Initializing privilege manager");
		for (PrivilegeEnum privilegeValEnum : PrivilegeEnum.values()) {
			createPrivilegeIfNotFound(privilegeValEnum);
		}
		
	}
	
	@Override
	public AclPrivilege createPrivilegeIfNotFound(PrivilegeEnum privilegeEnum) {
		Optional<AclPrivilege> privilegeOpt = privilegeRepository.findByPrivilegeName(privilegeEnum.getName());
		AclPrivilege aclPrivilege;
		if (privilegeOpt.isEmpty()) {
			aclPrivilege = privilegeRepository.save(new AclPrivilege(privilegeEnum.getName()));
		} else {
			aclPrivilege = privilegeOpt.get();
		}
		return aclPrivilege;
		
	}
	
	@Override
	public AclPrivilege getPrivilege(PrivilegeEnum privilegeEnum) {
		return createPrivilegeIfNotFound(privilegeEnum);
	}
}
