package com.ensimag.ridetrack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ensimag.ridetrack.models.acl.AclOidUserGroup;

@Repository
public interface AclOidUserGroupRepository extends JpaRepository<AclOidUserGroup, Long> {
	
	Optional<AclOidUserGroup> findByName(String name);
}
