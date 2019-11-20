package com.ensimag.ridetrack.repository;

import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.models.Space;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceRepository extends JpaRepository<Space, Long> {

	Optional<Space> findByNameAndOwner(String name, Client owner);
}
