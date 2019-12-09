package com.ensimag.ridetrack.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.models.Space;

public interface SpaceRepository extends JpaRepository<Space, Long> {

	Optional<Space> findByOwnerAndName(Client owner, String spaceName);
	
	Optional<Space> findByNameAndOwnerClientName(String clientName, String spaceName);
	
	List<Space> findAllByOwner(Client client);
	
	void deleteAllByOwnerClientName(String clientName);
}
