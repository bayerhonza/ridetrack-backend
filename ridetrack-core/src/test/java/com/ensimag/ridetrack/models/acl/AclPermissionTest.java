package com.ensimag.ridetrack.models.acl;
import static com.ensimag.ridetrack.auth.acl.AclPermission.DELETE;
import static com.ensimag.ridetrack.auth.acl.AclPermission.WRITE;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.ensimag.ridetrack.auth.acl.AclPermission;

class AclPermissionTest {
	
	@Test
	public void testMasks() {
		assertEquals(10, AclPermission.of(WRITE, DELETE));
	}
	
	@Test
	public void testMasks2() {
		assertEquals(0, AclPermission.noPrivilege());
	}
	
}