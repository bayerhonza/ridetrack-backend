package com.ensimag.ridetrack.rest.controllers;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ensimag.ridetrack.auth.RtUserManager;
import com.ensimag.ridetrack.auth.TokenProvider;
import com.ensimag.ridetrack.configuration.MethodSecurityConfig;
import com.ensimag.ridetrack.configuration.SecurityConfig;
import com.ensimag.ridetrack.auth.jwt.RestAuthenticationEntryPoint;
import com.ensimag.ridetrack.auth.TokenRequestFilter;
import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.repository.ClientRepository;
import com.ensimag.ridetrack.repository.RtUserRepository;
import com.ensimag.ridetrack.services.ClientManager;
import com.ensimag.ridetrack.services.SpaceUserManager;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SecurityConfig.class, ClientManager.class, MethodSecurityConfig.class })
@MockBean(RtUserRepository.class)
@MockBean(TokenRequestFilter.class)
@MockBean(RestAuthenticationEntryPoint.class)
@MockBean(RtUserManager.class)
@MockBean(SpaceUserManager.class)
@MockBean(TokenProvider.class)
class TestControllerSecurityTest {
	
	@Autowired
	ApplicationContext context;
	
	@MockBean
	private ClientRepository clientRepository;
	
	@Autowired
	private ClientManager clientManager;
	
	@Test
	public void getTestUnauthenticated() {
		assertThrows(AuthenticationCredentialsNotFoundException.class,
				() -> clientManager.findClient("testing"));
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void getTestAuthenticated() {
		when(clientRepository.findByClientName(anyString())).thenReturn(Optional.of(mock(Client.class)));
		clientManager.findClient("testing");
		verify(clientRepository,times(1)).findByClientName(anyString());
	}
}