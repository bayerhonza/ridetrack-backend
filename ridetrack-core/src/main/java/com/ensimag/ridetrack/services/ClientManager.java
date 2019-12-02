package com.ensimag.ridetrack.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ensimag.ridetrack.exception.RidetrackNotFoundException;
import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.repository.ClientRepository;

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
		spaceManager.createDefaultSpaceForClient(newClient);
	}
	
	public Client updateClient(Client client) {
		return clientRepository.save(client);
	}

	public boolean clientExists(String clientName) {
		return clientRepository.findByClientName(clientName).isPresent();
	}

	public Optional<Client> findClientByClientName(String clientName) {
		return clientRepository.findByClientName(clientName);
	}

	public void deleteClient(Client client) {
		clientRepository.delete(client);
	}
	
	public List<Client> findAll() {
		return this.clientRepository.findAll();
	}
	
	public Client findClientOrThrow(String clientName) {
		return findClientByClientName(clientName)
				.orElseThrow(() -> new RidetrackNotFoundException("Client" + clientName + " not found"));
	}
}
