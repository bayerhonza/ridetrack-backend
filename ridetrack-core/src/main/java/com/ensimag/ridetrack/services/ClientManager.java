package com.ensimag.ridetrack.services;

import com.ensimag.ridetrack.dto.ClientDef;
import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.repository.ClientRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
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

		clientRepository.save(newClient);
		return newClient;
	}

	public Optional<Client> findClientByClientName(String clientName) {
		return clientRepository.findByClientName(clientName);
	}

}
