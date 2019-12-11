package com.ensimag.ridetrack.auth;

import static com.ensimag.ridetrack.models.acl.AclPrivilege.CREATE;
import static com.ensimag.ridetrack.models.acl.AclPrivilege.DELETE;
import static com.ensimag.ridetrack.models.acl.AclPrivilege.READ;
import static com.ensimag.ridetrack.models.acl.AclPrivilege.WRITE;

import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import com.ensimag.ridetrack.models.ClientUser;
import com.ensimag.ridetrack.models.Space;
import com.ensimag.ridetrack.models.acl.AclEntry;
import com.ensimag.ridetrack.models.acl.AclPrivilege;
import com.ensimag.ridetrack.models.acl.AclSid;
import com.ensimag.ridetrack.repository.acl.AclEntryRepository;
import com.ensimag.ridetrack.repository.acl.AclObjectIdentityRepository;
import com.ensimag.ridetrack.repository.acl.AclSidRepository;

@Service
@Transactional
public class AclRegistrar {
	
	@Autowired
	private AclEntryRepository entryRepository;
	
	@Autowired
	private AclObjectIdentityRepository oidRepository;
	
	@Autowired
	private AclSidRepository sidRepository;
	
	public void registerNewClientUser(ClientUser clientUser) {
		Set<Space> clientSpaces = clientUser.getAssignedClient().getSpaces();
		Set<AclEntry> aclEntries = clientSpaces.stream()
				.map(space ->  new AclEntry().toBuilder()
						.oid(space.getOid())
						.sid(clientUser.getSid())
						.mask(AclPrivilege.of(WRITE, DELETE, CREATE, READ))
						.build())
				.collect(Collectors.toSet());
		entryRepository.saveAll(aclEntries);
	}
	
}
