package com.ensimag.ridetrack.repository.acl;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ensimag.ridetrack.models.acl.AclPrivilege;

@Repository
public interface AclPrivilegeRepository extends JpaRepository<AclPrivilege, Long> {

	Optional<AclPrivilege> findByPrivilegeName(String privilegeName);
}
