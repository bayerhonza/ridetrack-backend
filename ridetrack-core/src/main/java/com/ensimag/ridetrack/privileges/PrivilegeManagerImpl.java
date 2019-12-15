package com.ensimag.ridetrack.privileges;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ensimag.ridetrack.models.acl.AclPrivilege;
import com.ensimag.ridetrack.repository.acl.AclPrivilegeRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PrivilegeManagerImpl implements PrivilegeManager {
	
	private Map<PrivilegeEnum, AclPrivilege> privilegeToEntityMap = new HashMap<>();
	
	@Autowired
	private AclPrivilegeRepository privilegeRepository;
	
	@Override
	@Autowired
	public void init() {
		log.info("Initializing privilege manager");
		for (PrivilegeEnum privilegeValEnum : PrivilegeEnum.values()) {
			AclPrivilege privilege = createPrivilegeIfNotFound(privilegeValEnum);
			privilegeToEntityMap.put(privilegeValEnum, privilege);
		}
		
	}
	
	@Override
	public AclPrivilege createPrivilegeIfNotFound(PrivilegeEnum privilegeEnum) {
		Optional<AclPrivilege> privilegeOpt = privilegeRepository.findByPrivilegeName(privilegeEnum.getName());
		AclPrivilege aclPrivilege;
		if (privilegeOpt.isEmpty()) {
			aclPrivilege = privilegeRepository.save(new AclPrivilege(privilegeEnum.getName()));
			privilegeToEntityMap.put(privilegeEnum, aclPrivilege);
		} else {
			aclPrivilege = privilegeOpt.get();
		}
		privilegeToEntityMap.put(privilegeEnum, aclPrivilege);
		return aclPrivilege;
		
	}
	
	@Override
	public AclPrivilege getPrivilege(PrivilegeEnum privilegeEnum) {
		if (privilegeToEntityMap.containsKey(privilegeEnum)) {
			return privilegeToEntityMap.get(privilegeEnum);
		}
		return createPrivilegeIfNotFound(privilegeEnum);
	}
}
