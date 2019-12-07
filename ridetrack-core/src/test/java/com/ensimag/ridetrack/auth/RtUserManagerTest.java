package com.ensimag.ridetrack.auth;

import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import com.ensimag.ridetrack.models.Role;
import com.ensimag.ridetrack.models.User;
import com.ensimag.ridetrack.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class RtUserManagerTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private RtUserManager instance;

	@Test
	public void testLoadByUsername() {
		User user = User.builder()
			.password("hashedPassword")
			.username("username")
			.roles(Set.of(Role.of("TEST_ROLE","PRIVILEGE1", "PRIVILEGE2")))
			.build();
		when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

		UserDetails userDetails = instance.loadUserByUsername("tesing");

		assertEquals(2, userDetails.getAuthorities().size());
	}

}