package com.ensimag.ridetrack.services;

import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.models.Space;
import com.ensimag.ridetrack.repository.ClientRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClientManager {

	private final ClientRepository clientRepository;

	private final SpaceManager spaceManager;

	public ClientManager(ClientRepository clientRepository,
		SpaceManager spaceManager) {
		this.clientRepository = clientRepository;
		this.spaceManager = spaceManager;
	}

	public void createClient(Client newClient) {
		clientRepository.save(newClient);
		Space defaultSpace = spaceManager.createDefaultSpaceForClient(newClient);
	}

	public boolean clientExists(String clientName) {
		return clientRepository.findByClientName(clientName).isPresent();
	}

	public Optional<Client> findClientByClientName(String clientName) {
		return clientRepository.findByClientName(clientName);
	}

	public void deleteClient(String clientName) {
		clientRepository.deleteClientByClientName(clientName);
	}


}
