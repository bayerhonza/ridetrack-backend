package com.ensimag.ridetrack.privileges;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ensimag.ridetrack.models.acl.AclPrivilege;
import com.ensimag.ridetrack.repository.acl.AclPrivilegeRepository;

@Component
public class PrivilegeManager {
	
	private Map<PrivilegeEnum, AclPrivilege> privilegeToEntityMap = new HashMap<>();
	
	@Autowired
	private AclPrivilegeRepository privilegeRepository;
	
	@Autowired
	public void init() {
		for (PrivilegeEnum privilegeValEnum : PrivilegeEnum.values()) {
			AclPrivilege privilege = createPrivilegeIfNotFound(privilegeValEnum.getName());
			privilegeToEntityMap.put(privilegeValEnum, privilege);
		}
	}
	
	private AclPrivilege createPrivilegeIfNotFound(String privilege) {
		Optional<AclPrivilege> privilegeOpt = privilegeRepository.findByPrivilegeName(privilege);
		if (privilegeOpt.isEmpty()) {
			return privilegeRepository.save(new AclPrivilege(privilege));
		}
		return privilegeOpt.get();
	}
}
