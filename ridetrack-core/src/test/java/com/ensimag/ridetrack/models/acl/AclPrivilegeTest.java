package com.ensimag.ridetrack.models.acl;
import static com.ensimag.ridetrack.models.acl.AclPrivilege.DELETE;
import static com.ensimag.ridetrack.models.acl.AclPrivilege.WRITE;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AclPrivilegeTest {
	
	@Test
	public void testMasks() {
		assertEquals(10, AclPrivilege.of(WRITE, DELETE));
	}
	
	@Test
	public void testMasks2() {
		assertEquals(0, AclPrivilege.noPrivilege());
	}
	
}