package com.ensimag.ridetrack.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ensimag.ridetrack.models.ClientUser;

public interface ClientUserRepository extends JpaRepository<ClientUser, Long> {
	
	Optional<ClientUser> findByUsername(String username);
	
}
