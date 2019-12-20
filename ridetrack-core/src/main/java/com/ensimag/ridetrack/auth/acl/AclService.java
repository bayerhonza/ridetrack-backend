package com.ensimag.ridetrack.auth.acl;

import static com.ensimag.ridetrack.privileges.PrivilegeEnum.CAN_CREATE_DEVICE;
import static com.ensimag.ridetrack.privileges.PrivilegeEnum.CAN_CREATE_DEV_GROUP;
import static com.ensimag.ridetrack.privileges.PrivilegeEnum.CAN_CREATE_SPACE_USER;
import static com.ensimag.ridetrack.privileges.PrivilegeEnum.CAN_DELETE;
import static com.ensimag.ridetrack.privileges.PrivilegeEnum.CAN_DELETE_SPACES;
import static com.ensimag.ridetrack.privileges.PrivilegeEnum.CAN_READ;
import static com.ensimag.ridetrack.privileges.PrivilegeEnum.CAN_UPDATE;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.models.DeviceGroup;
import com.ensimag.ridetrack.models.RtUser;
import com.ensimag.ridetrack.models.Space;
import com.ensimag.ridetrack.models.SpaceUser;
import com.ensimag.ridetrack.models.acl.AclEntry;
import com.ensimag.ridetrack.models.acl.AclObjectIdentity;
import com.ensimag.ridetrack.models.acl.AclPrivilege;
import com.ensimag.ridetrack.models.acl.AclSid;
import com.ensimag.ridetrack.models.acl.AclUserGroup;
import com.ensimag.ridetrack.privileges.PrivilegeEnum;
import com.ensimag.ridetrack.privileges.PrivilegeManager;
import com.ensimag.ridetrack.repository.AclUserGroupRepository;
import com.ensimag.ridetrack.repository.acl.AclEntryRepository;
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
	private AclUserGroupRepository userGroupRepository;
	
	@Autowired
	private PrivilegeManager privilegeManager;
	
	public void registerNewClientUserGroup(Client client,String userGroupName) {
		AclUserGroup clientUserGroup = new AclUserGroup(userGroupName);
		userGroupRepository.save(clientUserGroup);
		Set<Space> clientSpaces = client.getSpaces();
		createEntryForEachOid(Set.of(client), clientUserGroup, Set.of(CAN_READ));
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
	
	public boolean evaluateSid(RtUser rtUser, AclObjectIdentity oid, AclPrivilege aclPrivilege) {
		Set<AclUserGroup> userGroups = rtUser.getUserGroups();
		boolean groupAccessGranted = userGroups
				.stream()
				.anyMatch(aclUserGroup -> isAuthorized(aclUserGroup, oid, aclPrivilege));
		
		if (groupAccessGranted) {
			return true;
		}
		
		return isAuthorized(rtUser, oid, aclPrivilege);
		
	}
	
	public void assignPrivilegeToUserGroup(AclObjectIdentity oid, AclUserGroup userGroup, PrivilegeEnum privilegeEnum) {
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
	
	public void deleteAllEntriesOfOid(AclObjectIdentity objectIdentity) {
		entryRepository.deleteAllByObjectIdentity(objectIdentity);
	}
	
	public AclPrivilege getAclPrivilegeByName(PrivilegeEnum privilegeEnum) {
		return privilegeManager.getPrivilege(privilegeEnum);
	}
	
	public List<AclEntry> getSidEntries(AclSid sidObject) {
		return entryRepository.findAllBySidObject(sidObject);
	}
	
}
