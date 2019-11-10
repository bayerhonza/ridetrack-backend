package com.ensimag.ridetrack.controllers;

import com.ensimag.ridetrack.auth.AuthenticationService;
import com.ensimag.ridetrack.auth.SuperRideTrackUser;
import com.ensimag.ridetrack.dto.ClientDef;
import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.services.ClientManager;
import com.ensimag.ridetrack.validation.ValidatorHelper;
import java.net.URI;
import java.util.Objects;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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

	private final ValidatorHelper validator;

	private final AuthenticationService authenticationService;

	public ClientController(ClientManager clientManager, ValidatorHelper validator,
		AuthenticationService authenticationService) {
		this.clientManager = clientManager;
		this.validator = validator;
		this.authenticationService = authenticationService;
	}

	@PostMapping(path = "/")
	public ResponseEntity<Client> createClient(@Valid @RequestBody ClientDef clientDef) {

		if (clientManager.findClientByClientName(clientDef.getClientName()).isPresent()) {
			log.error("Client {} already exists", clientDef.getClientName());
		}
		log.info("Creating client : {}", clientDef.getClientName());
		Client createdClient = validateAndCreateClient(clientDef);
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
				() -> new ResourceNotFoundException("Client not found on ::" + clientName));
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
