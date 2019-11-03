package com.ensimag.ridetrack.controllers;

import com.ensimag.ridetrack.dto.ClientDef;
import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.services.ClientManager;
import com.ensimag.ridetrack.validation.ValidatorHelper;
import java.net.URI;
import java.util.Objects;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class ClientController {

	private final ClientManager clientManager;

	private final ValidatorHelper validator;

	public ClientController(ClientManager clientManager, ValidatorHelper validator) {
		this.clientManager = clientManager;
		this.validator = validator;
	}

	@PostMapping("/")
	public ResponseEntity<Client> createClient(@RequestBody ClientDef clientDef)  {

		Client createdClient = validateAndCreateClient(clientDef);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(createdClient.getId())
			.toUri();
		return ResponseEntity.created(uri)
			.body(createdClient);
	}

	@GetMapping("/{clientName}")
	public ResponseEntity<Client> getClient(@PathVariable(name = "clientName") String clientName) {
		Optional<Client> clientOpt = clientManager.findClientByClientName(clientName);
		if(clientOpt.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
		}
		return ResponseEntity.ok()
			.body(clientOpt.get());
	}

	private Client validateAndCreateClient(ClientDef clientDef)  {
		if (Objects.isNull(clientDef.getClientName()) || !validator.validateUsername(clientDef.getClientName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong client name");
		}
		if (Objects.isNull(clientDef.getFullName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong client full name");
		}
		return clientManager.createClient(clientDef);
	}

}
