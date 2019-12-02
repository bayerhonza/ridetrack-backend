package com.ensimag.ridetrack.services;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ensimag.ridetrack.exception.RidetrackConflictException;
import com.ensimag.ridetrack.exception.RidetrackNotFoundException;
import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.models.DeviceGroup;
import com.ensimag.ridetrack.models.Space;
import com.ensimag.ridetrack.models.constants.RideTrackConstraint;
import com.ensimag.ridetrack.repository.SpaceRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Scope("prototype")
@Transactional
@Slf4j
public class SpaceManager {

	public static final String DEFAULT_SPACE_NAME = "rootSpace";

	private final SpaceRepository spaceRepository;
	
	private final DeviceGroupManager deviceGroupManager;
	
	public SpaceManager(
			SpaceRepository spaceRepository,
			DeviceGroupManager deviceGroupManager) {
		this.spaceRepository = spaceRepository;
		this.deviceGroupManager = deviceGroupManager;
		
	}
	
	public List<Space> findAllSpacesOfClient(Client client) {
		return spaceRepository.findAllByOwner(client);
	}
	
	public Optional<Space> findSpaceOfClient(Client client, String spaceName) {
		return spaceRepository.findByOwnerAndName(client, spaceName);
	}
	
	public boolean spaceExistsForClient(String clientName, String spaceName) {
		return spaceRepository.findByNameAndOwnerClientName(clientName, spaceName).isPresent();
	}
	
	public void createSpace(Client owner, Space newSpace) {
		newSpace.setOwner(owner);
		owner.addSpace(newSpace);
		initNewSpace(newSpace);
		spaceRepository.save(newSpace);
	}
	
	public Space createDefaultSpaceForClient(Client client) {
		return createSpace(client, DEFAULT_SPACE_NAME);
	}
	
	public Space createSpace(Client client, String name) {
		log.info("Creating space {} for {}", name, client.getClientName());
		if (spaceRepository.findByOwnerAndName(client, name).isPresent()) {
			log.error("Space {} of client {} already exists", name, client.getClientName());
			throw new RidetrackConflictException(Space.class, RideTrackConstraint.UQ_SPACE_CLIENT_SPACE_NAME,
					"Space " + name + " already defined");
		}
		Space newSpace = new Space().toBuilder()
				.name(name)
				.owner(client)
				.build();
		spaceRepository.save(newSpace);
		initNewSpace(newSpace);
		return newSpace;
	}
	
	public Space updateSpace(Space space) {
		return spaceRepository.save(space);
	}
	
	public void deleteSpace(Space space) {
		Client client = space.getOwner();
		client.removeSpace(space);
		spaceRepository.delete(space);
	}
	
	private void initNewSpace(Space space) {
		DeviceGroup defaultDevGroup = deviceGroupManager.createDefaultDeviceGroup(space);
		space.addDeviceGroup(defaultDevGroup);
	}
	
	public Space findSpaceOfClientOrThrow(Client client, String spaceName) {
		return findSpaceOfClient(client, spaceName)
				.orElseThrow(() -> new RidetrackNotFoundException("Space" + spaceName + " of " + client.getClientName() + " not found"));
	}
}
