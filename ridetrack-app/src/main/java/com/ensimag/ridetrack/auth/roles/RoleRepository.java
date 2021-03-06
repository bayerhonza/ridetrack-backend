package com.ensimag.ridetrack.auth.roles;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ensimag.ridetrack.models.Role;

/**
 * JPA repository of roles
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	

	Optional<Role> findByName(String roleName);
}
