package com.ensimag.ridetrack.services.impl;

import com.ensimag.ridetrack.exceptions.RidetrackException;
import com.ensimag.ridetrack.models.Space;
import com.ensimag.ridetrack.models.User;
import com.ensimag.ridetrack.repository.SpaceRepository;
import com.ensimag.ridetrack.services.SpaceService;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SpaceServiceImpl implements SpaceService {



	@Autowired
	private SpaceRepository spaceRepository;

	@Override
	public void addUserToSpace(User user, Long spaceId) throws RidetrackException {
		Optional<Space> optionalSpace = spaceRepository.findById(spaceId);
		if (optionalSpace.isPresent()) {
			Set<User> users = optionalSpace.get().getUsers();
			users.add(user);
		} else {
			log.debug("Space with id: {} not found", spaceId);
			throw new RidetrackException("Space not found");
		}
	}

	@Override
	public void removeUserFromSpace(User user, Long spaceId) throws RidetrackException {
      Optional<Space> optionalSpace = spaceRepository.findById(spaceId);
      if (optionalSpace.isPresent()) {
        Set<User> users = optionalSpace.get().getUsers();
        users.add(user);
      } else {
        log.debug("Space with id: {} not found", spaceId);
        throw new RidetrackException("Space not found");
      }
	}
}
