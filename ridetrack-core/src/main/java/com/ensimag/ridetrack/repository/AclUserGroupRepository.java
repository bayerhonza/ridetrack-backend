package com.ensimag.ridetrack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ensimag.ridetrack.models.acl.AclUserGroup;

@Repository
public interface AclUserGroupRepository extends JpaRepository<AclUserGroup, Long> {
	
	Optional<AclUserGroup> findByName(String name);
}
