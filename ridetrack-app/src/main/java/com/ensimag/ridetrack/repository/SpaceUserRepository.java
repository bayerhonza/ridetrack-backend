package com.ensimag.ridetrack.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ensimag.ridetrack.models.SpaceUser;

@Repository
public interface SpaceUserRepository extends JpaRepository<SpaceUser, Long> {
	Optional<SpaceUser> findByUsername(String username);
}
