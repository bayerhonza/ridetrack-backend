package com.ensimag.ridetrack.rest.controllers;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ensimag.ridetrack.dto.ClientDTO;
import com.ensimag.ridetrack.exception.RidetrackConflictException;
import com.ensimag.ridetrack.mappers.ClientMapper;
import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.models.constants.RideTrackConstraint;
import com.ensimag.ridetrack.rest.api.RestPaths;
import com.ensimag.ridetrack.services.ClientManager;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(RestPaths.API_PATH)
@Slf4j
public class ClientController {
	
	private final ClientManager clientManager;
	
	private final ClientMapper clientMapper;
	
	@Autowired
	public ClientController(
			ClientManager clientManager,
			ClientMapper clientMapper) {
		this.clientManager = clientManager;
		this.clientMapper = clientMapper;
		
	}
	
	@PostMapping(path = "/client")
	public ResponseEntity<ClientDTO> createClient(@Valid @RequestBody ClientDTO clientDTO) {
		if (clientManager.clientExists(clientDTO.getClientName())) {
			log.warn("Client {} already exists", clientDTO.getClientName());
			throw new RidetrackConflictException(Client.class, RideTrackConstraint.UQ_CLIENT_CLIENT_NAME, "Client already defined");
		}
		log.info("Creating client : {}", clientDTO.getClientName());
		Client newClient = clientMapper.toClient(clientDTO);
		
		newClient.setFullName(clientDTO.getFullName());
		newClient.setClientName(clientDTO.getClientName());
		clientManager.createClient(newClient);
		log.debug("Created client : {}", clientDTO.getClientName());
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(newClient.getId())
				.toUri();
		ClientDTO resultClientDTO = clientMapper.toClientDTO(newClient);
		return ResponseEntity.created(uri)
				.body(resultClientDTO);
	}
	
	@GetMapping("/clients")
	public List<ClientDTO> getAllClients() {
		return clientManager.findAll().stream()
				.map(clientMapper::toClientDTO)
				.collect(Collectors.toList());
	}
	
	@GetMapping("/client/{clientName}")
	public ResponseEntity<ClientDTO> getClient(@PathVariable(name = "clientName") String clientName) {
		Client client = clientManager.findClientOrThrow(clientName);
		return ResponseEntity.ok()
				.body(clientMapper.toClientDTO(client));
	}
	
	@PutMapping("/client/{clientName}")
	public ResponseEntity<ClientDTO> updateClient(@PathVariable(value = "clientName") String clientName,
			@Valid @RequestBody ClientDTO clientDetails) {
		Client client = clientManager.findClientOrThrow(clientName);
		client.setClientName(clientDetails.getClientName());
		client.setFullName(clientDetails.getFullName());
		
		log.info("Updating client {}", clientName);
		final Client updatedClient = clientManager.updateClient(client);
		return ResponseEntity.ok(clientMapper.toClientDTO(updatedClient));
	}
	
	@DeleteMapping("/client/{clientName}")
	public Map<String, Boolean> delete(@PathVariable(name = "clientName") String clientName) {
		Client client = clientManager.findClientOrThrow(clientName);
		
		log.info("Deleting client {}", clientName);
		clientManager.deleteClient(client);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
}
