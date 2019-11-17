package com.ensimag.ridetrack.services;

import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.repository.ClientRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClientManager {

	private final ClientRepository clientRepository;

	public ClientManager(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	public Client createClient(Client newClient) {
		clientRepository.saveAndFlush(newClient);
		return newClient;
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
