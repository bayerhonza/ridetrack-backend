package com.ensimag.ridetrack.auth.acl;

import static com.ensimag.ridetrack.privileges.PrivilegeEnum.CAN_CREATE_DEVICE;
import static com.ensimag.ridetrack.privileges.PrivilegeEnum.CAN_CREATE_DEV_GROUP;
import static com.ensimag.ridetrack.privileges.PrivilegeEnum.CAN_CREATE_SPACE_USER;
import static com.ensimag.ridetrack.privileges.PrivilegeEnum.CAN_DELETE;
import static com.ensimag.ridetrack.privileges.PrivilegeEnum.CAN_DELETE_SPACES;
import static com.ensimag.ridetrack.privileges.PrivilegeEnum.CAN_READ;
import static com.ensimag.ridetrack.privileges.PrivilegeEnum.CAN_UPDATE;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ensimag.ridetrack.exception.RidetrackNotFoundException;
import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.models.ClientUser;
import com.ensimag.ridetrack.models.DeviceGroup;
import com.ensimag.ridetrack.models.Space;
import com.ensimag.ridetrack.models.SpaceUser;
import com.ensimag.ridetrack.models.acl.AclEntry;
import com.ensimag.ridetrack.models.acl.AclObjectIdentity;
import com.ensimag.ridetrack.models.acl.AclOidUserGroup;
import com.ensimag.ridetrack.models.acl.AclPrivilege;
import com.ensimag.ridetrack.models.acl.AclSid;
import com.ensimag.ridetrack.privileges.PrivilegeEnum;
import com.ensimag.ridetrack.privileges.PrivilegeManager;
import com.ensimag.ridetrack.repository.AclOidUserGroupRepository;
import com.ensimag.ridetrack.repository.acl.AclEntryRepository;
import com.ensimag.ridetrack.repository.acl.AclObjectIdentityRepository;
import com.ensimag.ridetrack.repository.acl.AclPrivilegeRepository;
import com.ensimag.ridetrack.repository.acl.AclSidRepository;
import com.google.common.collect.ImmutableSet;

@Service
@Transactional
public class AclService {
	
	private static final Set<PrivilegeEnum> defaultClientSpacePrivileges = ImmutableSet.of(
			CAN_CREATE_DEV_GROUP,
			CAN_CREATE_SPACE_USER,
			CAN_CREATE_DEVICE,
			CAN_DELETE_SPACES,
			CAN_DELETE,
			CAN_UPDATE,
			CAN_READ
	);
	
	private static final Set<PrivilegeEnum> defaultSpaceClientPrivileges = ImmutableSet.of(
			CAN_CREATE_DEVICE,
			CAN_DELETE,
			CAN_UPDATE,
			CAN_READ
	);
	
	@Autowired
	private AclEntryRepository entryRepository;
	
	@Autowired
	private AclOidUserGroupRepository userGroupRepository;
	
	@Autowired
	private PrivilegeManager privilegeManager;
	
	public void registerNewClientUserGroup(Client client,String userGroupName) {
		AclOidUserGroup clientUserGroup = new AclOidUserGroup(userGroupName);
		userGroupRepository.save(clientUserGroup);
		Set<Space> clientSpaces = client.getSpaces();
		createEntryForEachOid(clientSpaces, clientUserGroup, defaultClientSpacePrivileges);
	}
	
	public void registerNewSpaceUser(Space space, SpaceUser spaceUser) {
		Set<DeviceGroup> deviceGroups = space.getDeviceGroups();
		createEntryForEachOid(deviceGroups, spaceUser, defaultClientSpacePrivileges);
	}
	
	public void createEntryForEachOid(Set<? extends AclObjectIdentity> objectIdentities, AclSid sid, Set<PrivilegeEnum> privilegeEnums) {
		Set<AclEntry> aclEntries = objectIdentities.stream()
				.map(oid -> privilegeEnums.stream()
						.map(s -> createAclEntry(oid, sid, s))
						.collect(Collectors.toSet()))
				.flatMap(Collection::stream)
				.collect(Collectors.toSet());
		entryRepository.saveAll(aclEntries);
	}
	
	public boolean isAuthorized(AclSid aclSid, AclObjectIdentity aclObjectIdentity, AclPrivilege privilege) {
		return !entryRepository
				.findAclEntriesBySidObjectAndObjectIdentityAndPrivilege(aclSid, aclObjectIdentity, privilege)
				.isEmpty();
	}
	
	public void assignPrivilegeToUserGroup(AclObjectIdentity oid, AclOidUserGroup userGroup, PrivilegeEnum privilegeEnum) {
		AclEntry entry = createAclEntry(oid, userGroup, privilegeEnum);
		entryRepository.save(entry);
	}
	
	private AclEntry createAclEntry(AclObjectIdentity oid, AclSid sid, PrivilegeEnum privilegeEnum) {
		return new AclEntry().toBuilder()
				.objectIdentity(oid)
				.sidObject(sid)
				.privilege(privilegeManager.getPrivilege(privilegeEnum))
				.build();
	}
	
	public AclPrivilege getAclPrivilegeByName(PrivilegeEnum privilegeEnum) {
		return privilegeManager.getPrivilege(privilegeEnum);
	}
	
}
