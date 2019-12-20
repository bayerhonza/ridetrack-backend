package com.ensimag.ridetrack.services;

import static com.ensimag.ridetrack.privileges.PrivilegeEnum.CAN_READ;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ensimag.ridetrack.auth.acl.AclService;
import com.ensimag.ridetrack.exception.RidetrackConflictException;
import com.ensimag.ridetrack.exception.RidetrackNotFoundException;
import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.models.DeviceGroup;
import com.ensimag.ridetrack.models.Space;
import com.ensimag.ridetrack.models.acl.AclUserGroup;
import com.ensimag.ridetrack.models.constants.RideTrackConstraint;
import com.ensimag.ridetrack.repository.AclUserGroupRepository;
import com.ensimag.ridetrack.repository.SpaceRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class SpaceManager {
	
	public static final String DEFAULT_SPACE_NAME = "rootSpace";
	
	public static final String DEFAULT_SPACE_USER_GROUP = "_DefGroup";
	
	private final SpaceRepository spaceRepository;
	
	private final DeviceGroupManager deviceGroupManager;
	
	@Autowired
	private AclService aclService;
	
	@Autowired
	private AclUserGroupRepository userGroupRepository;
	
	public SpaceManager(
			SpaceRepository spaceRepository,
			DeviceGroupManager deviceGroupManager) {
		this.spaceRepository = spaceRepository;
		this.deviceGroupManager = deviceGroupManager;
		
	}
	
	@PostFilter("hasPermission(filterObject, T(com.ensimag.ridetrack.privileges.PrivilegeEnum).CAN_READ)")
	public List<Space> findAllSpacesOfClient(Client client) {
		return spaceRepository.findAllByOwner(client);
	}
	
	@PostAuthorize("hasPermission(returnObject.orElse(null), T(com.ensimag.ridetrack.privileges.PrivilegeEnum).CAN_READ)")
	public Optional<Space> findSpaceOfClient(Client client, String spaceName) {
		return spaceRepository.findByOwnerAndName(client, spaceName);
	}
	
	public boolean spaceExistsForClient(Client client, String spaceName) {
		return spaceRepository.findByOwnerAndName(client, spaceName).isPresent();
	}
	
	public Space createDefaultSpaceForClient(Client client) {
		return createSpace(client, getDefaultSpaceName());
		
	}
	
	@PreAuthorize("hasPermission(#client, T(com.ensimag.ridetrack.privileges.PrivilegeEnum).CAN_CREATE_SPACE)")
	public Space createSpace(Client client, String name) {
		log.info("Creating space '{}@{}'", name, client.getClientName());
		checkIfSpaceExists(client, name);
		
		Space newSpace = new Space().toBuilder()
				.name(name)
				.build();
		newSpace.setOwner(client);
		
		createSpace(newSpace);
		return newSpace;
	}
	
	public void createSpace(Space newSpace) {
		spaceRepository.save(newSpace);
		initDefaultDeviceGroup(newSpace);
		createDefaultUserGroup(newSpace);
		
	}
	
	public void createDefaultUserGroup(Space space) {
		AclUserGroup userGroup = new AclUserGroup(getSpaceDefaultUGroupName(space));
		userGroupRepository.save(userGroup);
		aclService.assignPrivilegeToUserGroup(space, userGroup, CAN_READ);
		space.getDeviceGroups()
				.forEach(devGroup -> aclService.assignPrivilegeToUserGroup(devGroup, userGroup, CAN_READ));
	}
	
	@PreAuthorize("hasPermission(#space, T(com.ensimag.ridetrack.privileges.PrivilegeEnum).CAN_UPDATE)")
	public Space updateSpace(Space space) {
		return spaceRepository.save(space);
	}
	
	@PreAuthorize("hasPermission(#space, 'CAN_DELETE')")
	public void deleteSpace(Space space) {
		Client client = space.getOwner();
		client.removeSpace(space);
		spaceRepository.delete(space);
	}
	
	@PreAuthorize("hasPermission(#client, T(com.ensimag.ridetrack.privileges.PrivilegeEnum).CAN_DELETE_SPACES)")
	public void deleteClientSpaces(Client client) {
		List<Space> spaces = spaceRepository.findAllByOwner(client);
		spaces.forEach(space -> {
			deviceGroupManager.deleteDeviceGroups(space);
			aclService.deleteAllEntriesOfOid(space);
			spaceRepository.delete(space);
		});
	}
	
	public void initDefaultDeviceGroup(Space space) {
		DeviceGroup defaultDevGroup = deviceGroupManager.createDefaultDeviceGroup(space);
		space.addDeviceGroup(defaultDevGroup);
		spaceRepository.save(space);
	}
	
	@PostAuthorize("hasPermission(returnObject,  T(com.ensimag.ridetrack.privileges.PrivilegeEnum).CAN_READ)")
	public Space findSpaceOfClientOrThrow(Client client, String spaceName) {
		return findSpaceOfClient(client, spaceName)
				.orElseThrow(() -> new RidetrackNotFoundException("Space" + spaceName + " of " + client.getClientName() + " not found"));
	}
	
	public void checkIfSpaceExists(Client owner, String spaceName) {
		try {
			findSpaceOfClientOrThrow(owner, spaceName);
			throw new RidetrackConflictException(Space.class, RideTrackConstraint.UQ_SPACE_CLIENT_SPACE_NAME, "Space " + spaceName + " already defined");
		} catch (RidetrackNotFoundException ex) {
			// ok pour check
		}
	}
	
	public String getSpaceDefaultUGroupName(Space space) {
		return space.getOwner().getClientName() + "_" + space.getName() + DEFAULT_SPACE_USER_GROUP;
	}
	
	public String getDefaultSpaceName() {
		return DEFAULT_SPACE_NAME;
	}
}
