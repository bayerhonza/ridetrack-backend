package com.ensimag.ridetrack.services;

import com.ensimag.ridetrack.dto.SpaceDef;
import com.ensimag.ridetrack.models.Space;
import com.ensimag.ridetrack.repository.SpaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SpaceManager {

	private final SpaceRepository spaceRepository;


	public SpaceManager(SpaceRepository spaceRepository) {
		this.spaceRepository = spaceRepository;
	}

	public Space createSpace(SpaceDef spaceDef) {
		Space newSpace = Space.builder()
			.name(spaceDef.getName())
			.build();

		spaceRepository.save(newSpace);
		return newSpace;
	}
}
