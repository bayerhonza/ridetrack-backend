package com.ensimag.ridetrack.auth;

import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import com.ensimag.ridetrack.models.Role;
import com.ensimag.ridetrack.models.SpaceUser;
import com.ensimag.ridetrack.repository.RtUserRepository;

@ExtendWith(MockitoExtension.class)
class RtSpaceUserManagerTest {

	@Mock
	private RtUserRepository rtUserRepository;

	@InjectMocks
	private RtUserManager instance;

	@Test
	public void testLoadByUsername() {
		SpaceUser user = SpaceUser.builder()
			.password("hashedPassword")
			.username("username")
			.build();
		user.addRole(Role.of("TEST_ROLE"));
		when(rtUserRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

		UserDetails userDetails = instance.loadUserByUsername("tesing");

		assertEquals(0, userDetails.getAuthorities().size());
	}

}