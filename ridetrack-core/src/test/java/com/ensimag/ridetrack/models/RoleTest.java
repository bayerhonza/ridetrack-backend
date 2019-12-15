package com.ensimag.ridetrack.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.ensimag.ridetrack.models.acl.AclPrivilege;

class RoleTest {

	@Test
	public void testRoleOf() {
		Set<String> privileges = Set.of(
				"PRIVILEGE1",
				"PRIVILEGE2",
				"PRIVILEGE3"
		);
		
		AclPrivilege[] aclPrivileges = (AclPrivilege[]) privileges.stream()
				.map(AclPrivilege::new)
				.toArray();
		Role role = Role.of("TEST_ROLE", aclPrivileges);
		assertEquals(3, role.getAclPrivileges().size());
		assertTrue(role.getAclPrivileges().stream()
			.anyMatch(privilege -> privilege.getPrivilegeName().equals("PRIVILEGE2")));
	}

	@Test
	public void testAddPrivilege() {
		Role role = Role.of("TEST_ROLE");
		AclPrivilege aclPrivilege = AclPrivilege.of("PRIVILEGE1");
		role.setAclPrivileges(null);
		role.addPrivilege(aclPrivilege);
		assertNotNull(role.getAclPrivileges());
	}

}