package com.ensimag.ridetrack.services.impl;

import com.ensimag.ridetrack.exceptions.RidetrackException;
import com.ensimag.ridetrack.models.Space;
import com.ensimag.ridetrack.models.User;
import com.ensimag.ridetrack.repository.SpaceRepository;
import com.ensimag.ridetrack.repository.UserRepository;
import com.ensimag.ridetrack.services.SpaceService;
import java.util.Set;
import javax.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SpaceServiceImpl implements SpaceService {


	private final SpaceRepository spaceRepository;

	private final EntityManager entityManager;

	private final UserRepository userRepository;

	public SpaceServiceImpl(SpaceRepository spaceRepository,
		EntityManager entityManager,
		UserRepository userRepository) {
		this.spaceRepository = spaceRepository;
		this.entityManager = entityManager;
		this.userRepository = userRepository;
	}

	public void addUserToSpace(User user, Long spaceId) throws RidetrackException {
		Space space = spaceRepository.findById(spaceId)
			.orElseThrow(
				() -> new RidetrackException("Space not found")
			);

		Set<User> users = space.getUsers();
		users.add(user);
	}

	public void removeUserFromSpace(String username) throws RidetrackException {
		User user = userRepository.findByUsername(username)
			.orElseThrow(
				() -> new RidetrackException("User " + username + " not found")
			);
		user.getSpace().getUsers().remove(user);
		user.setSpace(null);
		entityManager.merge(user);


	}
}
