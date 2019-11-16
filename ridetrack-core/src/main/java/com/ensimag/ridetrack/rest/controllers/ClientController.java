package com.ensimag.ridetrack.rest.controllers;

import com.ensimag.ridetrack.auth.AuthenticationService;
import com.ensimag.ridetrack.auth.SuperRideTrackUser;
import com.ensimag.ridetrack.dto.ClientDef;
import com.ensimag.ridetrack.exception.RidetrackConflictException;
import com.ensimag.ridetrack.exception.RidetrackNotFoundException;
import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.models.constants.RideTrackConstraint;
import com.ensimag.ridetrack.repository.ClientRepository;
import com.ensimag.ridetrack.services.ClientManager;
import java.net.URI;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/client")
@Slf4j
public class ClientController {

	private final ClientManager clientManager;

	private final ClientRepository clientRepository;

	private final AuthenticationService authenticationService;

	@Autowired
	public ClientController(
		ClientManager clientManager,
		AuthenticationService authenticationService,
		ClientRepository clientRepository) {
		this.clientManager = clientManager;
		this.authenticationService = authenticationService;
		this.clientRepository = clientRepository;
	}

	@PostMapping(path = "/")
	public ResponseEntity<Client> createClient(@Valid @RequestBody ClientDef clientDef) {
		if (clientManager.clientExists(clientDef)) {
			log.warn("Client {} already exists", clientDef.getClientName());
			throw new RidetrackConflictException(Client.class, RideTrackConstraint.UQ_CLIENT_CLIENT_NAME, "Client already defined");
		}
		log.info("Creating client : {}", clientDef.getClientName());
		Client createdClient = clientManager.createClient(clientDef);
		log.debug("Created client : {}", clientDef.getClientName());

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(createdClient.getId())
			.toUri();
		return ResponseEntity.created(uri)
			.body(createdClient);
	}

	@GetMapping("/{clientName}")
	public ResponseEntity<Client> getClient(@PathVariable(name = "clientName") String clientName) {
		Client client = clientManager.findClientByClientName(clientName)
			.orElseThrow(
				() -> new RidetrackNotFoundException("Client" + clientName + " not found"));
		return ResponseEntity.ok()
			.body(client);
	}

	@DeleteMapping("/{clientName}")
	public void delete(@Valid @PathVariable(name = "clientName") String clientName) {
		// TODO do the authentication properly
		if (!authenticationService.isAllowedToDelete(SuperRideTrackUser.getSuperUser())) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		clientManager.deleteClient(clientName);
	}


}
