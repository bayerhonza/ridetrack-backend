package com.ensimag.ridetrack.services;

import com.ensimag.ridetrack.dto.ClientDef;
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

	public Client createClient(ClientDef clientDef) {
		Client newClient = Client.builder()
			.clientName(clientDef.getClientName())
			.fullName(clientDef.getFullName())
			.build();

		clientRepository.saveAndFlush(newClient);
		return newClient;
	}

	public boolean clientExists(ClientDef clientDef) {
		return clientRepository.findByClientName(clientDef.getClientName()).isPresent();
	}

	public Optional<Client> findClientByClientName(String clientName) {
		return clientRepository.findByClientName(clientName);
	}

	public void deleteClient(String clientName) {
		clientRepository.deleteClientByClientName(clientName);
	}


}
