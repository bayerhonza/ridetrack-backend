package com.ensimag.ridetrack.repository.acl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ensimag.ridetrack.models.acl.AclEntry;

@Repository
public interface AclEntryRepository extends JpaRepository<AclEntry,Long> {


	
}
