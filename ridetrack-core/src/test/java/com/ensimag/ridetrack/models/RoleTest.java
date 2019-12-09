package com.ensimag.ridetrack.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class RoleTest {

	@Test
	public void testRoleOf() {
		Role role = Role.of("TEST_ROLE", "PRIVILEGE1", "PRIVILEGE2", "PRIVILEGE3");
		assertEquals(3, role.getPrivileges().size());
		assertTrue(role.getPrivileges().stream()
			.anyMatch(privilege -> privilege.getPrivilegeName().equals("PRIVILEGE2")));
	}

	@Test
	public void testAddPrivilege() {
		Role role = Role.of("TEST_ROLE");
		Privilege privilege = Privilege.of("PRIVILEGE1");
		role.setPrivileges(null);
		role.addPrivilege(privilege);
		assertNotNull(role.getPrivileges());
	}

}