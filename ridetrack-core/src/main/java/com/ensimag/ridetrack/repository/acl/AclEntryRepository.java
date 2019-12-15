package com.ensimag.ridetrack.repository.acl;
import java.security.acl.Acl;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ensimag.ridetrack.models.acl.AclEntry;
import com.ensimag.ridetrack.models.acl.AclObjectIdentity;
import com.ensimag.ridetrack.models.acl.AclPrivilege;
import com.ensimag.ridetrack.models.acl.AclSid;

@Repository
public interface AclEntryRepository extends JpaRepository<AclEntry, Long> {
	
	List<AclEntry> findAclEntriesBySidObjectAndObjectIdentityAndPrivilege(AclSid sid, AclObjectIdentity aclObjectIdentity, AclPrivilege privilege);
	
}
