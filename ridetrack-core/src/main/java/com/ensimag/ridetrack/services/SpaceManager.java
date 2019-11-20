package com.ensimag.ridetrack.services;

import com.ensimag.ridetrack.exception.RidetrackConflictException;
import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.models.DeviceGroup;
import com.ensimag.ridetrack.models.Space;
import com.ensimag.ridetrack.models.constants.RideTrackConstraint;
import com.ensimag.ridetrack.repository.SpaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public void createSpace(Space newSpace, Client owner) {
		newSpace.setOwner(owner);
		initNewSpace(newSpace);
		spaceRepository.save(newSpace);
	}

	public Space createDefaultSpaceForClient(Client client) {
		return createSpace(DEFAULT_SPACE_NAME, client);
	}

	public Space createSpace(String name, Client client) {
		log.info("Creating space {} for {}", name, client.getClientName());
		if (spaceRepository.findByNameAndOwner(name, client).isPresent()) {
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

	private void initNewSpace(Space space) {
		DeviceGroup defaultDevGroup = deviceGroupManager.createDefaultDeviceGroup(space);
		space.addDeviceGroup(defaultDevGroup);
	}
}
