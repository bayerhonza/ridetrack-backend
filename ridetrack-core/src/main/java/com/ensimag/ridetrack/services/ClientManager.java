package com.ensimag.ridetrack.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ensimag.ridetrack.auth.acl.AclService;
import com.ensimag.ridetrack.exception.RidetrackNotFoundException;
import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.models.Space;
import com.ensimag.ridetrack.models.acl.AclOidUserGroup;
import com.ensimag.ridetrack.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ClientManager {
	
	public static final String DEFAULT_CLIENT_USER_GROUP = "_def_client_ugroup";
	
	private final ClientRepository clientRepository;
	
	@Autowired
	private AclService aclService;
	
	private final SpaceManager spaceManager;
	
	public ClientManager(ClientRepository clientRepository,
			SpaceManager spaceManager) {
		this.clientRepository = clientRepository;
		this.spaceManager = spaceManager;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	public void createClient(Client newClient) {
		log.info("Creating client '{}'", newClient.getClientName());
		clientRepository.save(newClient);
		spaceManager.createDefaultSpaceForClient(newClient);
		
		aclService.registerNewClientUserGroup(newClient, getDefaultClientUserGroupName(newClient));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	public Client updateClient(Client client) {
		log.info("Updating client '{}'", client.getClientName());
		return clientRepository.save(client);
	}
	
	public boolean clientExists(String clientName) {
		return findClientByClientName(clientName).isPresent();
	}
	
	@PostAuthorize("hasPermission(returnObject.get(),T(com.ensimag.ridetrack.privileges.PrivilegeEnum).CAN_READ)")
	public Optional<Client> findClientByClientName(String clientName) {
		return clientRepository.findByClientName(clientName);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteClient(Client client) {
		log.info("Removing client '{}'", client.getClientName());
		spaceManager.deleteClientSpaces(client);
		clientRepository.delete(client);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	public List<Client> findAll() {
		return this.clientRepository.findAll();
	}
	
	@PostAuthorize("hasPermission(returnObject, T(com.ensimag.ridetrack.privileges.PrivilegeEnum).CAN_READ)")
	public Client findClientOrThrow(String clientName) {
		return findClientByClientName(clientName)
				.orElseThrow(() -> new RidetrackNotFoundException("Client" + clientName + " not found"));
	}
	
	public String getDefaultClientUserGroupName(Client client) {
		return client.getClientName() + DEFAULT_CLIENT_USER_GROUP;
	}
}
