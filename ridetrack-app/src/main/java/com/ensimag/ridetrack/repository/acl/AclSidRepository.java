package com.ensimag.ridetrack.repository.acl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ensimag.ridetrack.models.acl.AclSid;

@Repository
public interface AclSidRepository extends JpaRepository<AclSid, Long> {
	
}
