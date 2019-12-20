package com.ensimag.ridetrack.services;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ensimag.ridetrack.exception.RidetrackNotFoundException;
import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.repository.ClientRepository;

@ExtendWith(MockitoExtension.class)
class ClientManagerTest {
	
	@Mock
	private ClientRepository clientRepository;
	
	@Mock
	private SpaceManager spaceManager;
	
	@InjectMocks
	private ClientManager instance;
	
	@Test
	public void testFindNonExistingClient() {
		when(clientRepository.findByClientName("notPresent")).thenReturn(Optional.empty());
		Assertions.assertThrows(RidetrackNotFoundException.class,
				() -> instance.findClient("notPresent"));
	}
	
	@Test
	public void testCreateClient() {
		Client newClient = new Client().toBuilder()
				.clientName("client1")
				.fullName("Client One")
				.build();
		
		instance.createClient(newClient);
		
		verify(clientRepository).save(newClient);
		verify(spaceManager).createDefaultSpaceForClient(newClient);
	}
	
	@Test
	public void testUpdateClient() {
		Client newClient = new Client().toBuilder()
				.clientName("client1")
				.fullName("Client One")
				.build();
		
		instance.updateClient(newClient);
		verify(clientRepository).save(newClient);
	}
	
	@Test
	public void testDeleteClient() {
		Client newClient = new Client().toBuilder()
				.clientName("client1")
				.fullName("Client One")
				.build();
		
		instance.deleteClient(newClient);
		verify(spaceManager).deleteClientSpaces(newClient);
		verify(clientRepository).delete(newClient);
	}
}