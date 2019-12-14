package com.ensimag.ridetrack.rest.controllers;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ensimag.ridetrack.dto.SpaceDTO;
import com.ensimag.ridetrack.exception.RidetrackConflictException;
import com.ensimag.ridetrack.mappers.SpaceMapper;
import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.models.Space;
import com.ensimag.ridetrack.models.constants.RideTrackConstraint;
import com.ensimag.ridetrack.rest.api.RestPaths;
import com.ensimag.ridetrack.services.ClientManager;
import com.ensimag.ridetrack.services.SpaceManager;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(RestPaths.API_PATH)
@PreAuthorize("hasRole('USER')")
@Slf4j
public class SpaceController {
	
	@Autowired
	private SpaceManager spaceManager;
	
	@Autowired
	private ClientManager clientManager;
	
	@Autowired
	private SpaceMapper spaceMapper;
	
	@GetMapping("/spaces/{clientName}")
	public List<SpaceDTO> getAllSpacesOfClient(
			@PathVariable("clientName") String clientName) {
		Client client = clientManager.findClientOrThrow(clientName);
		return spaceManager.findAllSpacesOfClient(client).stream()
				.map(spaceMapper::toSpaceDTO)
				.collect(Collectors.toList());
	}
	
	@GetMapping("/space/{clientName}/{spaceName}")
	public List<SpaceDTO> getSpaceOfClient(
			@PathVariable("clientName") String clientName,
			@PathVariable("spaceName") String spaceName) {
		Client client = clientManager.findClientOrThrow(clientName);
		return spaceManager.findSpaceOfClient(client, spaceName).stream()
				.map(spaceMapper::toSpaceDTO)
				.collect(Collectors.toList());
	}
	
	@PostMapping(path = "/space")
	@PreAuthorize("hasRole('CLIENT')")
	public ResponseEntity<SpaceDTO> createSpace(@Valid @RequestBody SpaceDTO spaceDTO) {
		Client client = clientManager.findClientOrThrow(spaceDTO.getClientName());
		if (spaceManager.spaceExistsForClient(client, spaceDTO.getName())) {
			log.warn("Space '{}@{}' already exists", spaceDTO.getName(), spaceDTO.getClientName());
			throw new RidetrackConflictException(Client.class, RideTrackConstraint.UQ_CLIENT_CLIENT_NAME, "Client already defined");
		}
		
		log.info("Creating space '{}@{}'", spaceDTO.getName(), spaceDTO.getClientName());
		Space newSpace = spaceMapper.toSpace(spaceDTO);
		
		newSpace.setOwner(client);
		spaceManager.createSpace( newSpace);
		
		log.debug("Created space '{}'", newSpace.getName());
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(newSpace.getOid())
				.toUri();
		return ResponseEntity.created(uri)
				.body(spaceMapper.toSpaceDTO(newSpace));
	}
	
	@PutMapping("/space/{clientName}/{spaceName}")
	@PreAuthorize("hasRole('CLIENT')")
	public ResponseEntity<SpaceDTO> updateSpace(@PathVariable("clientName") String clientName,
			@PathVariable("spaceName") String spaceName,
			@Valid @RequestBody SpaceDTO spaceDetails) {
		Client client = clientManager.findClientOrThrow(clientName);
		Space space = spaceManager.findSpaceOfClientOrThrow(client, spaceName);
		space.setName(spaceDetails.getName());
		log.info("Updating space {}", space);
		Space updatedSpace = spaceManager.updateSpace(space);
		return ResponseEntity.ok(spaceMapper.toSpaceDTO(updatedSpace));
	}
	
	@DeleteMapping("/space/{clientName}/{spaceName}")
	@PreAuthorize("hasRole('CLIENT')")
	public Map<String, Boolean> delete(
			@PathVariable(name = "clientName") String clientName,
			@PathVariable(name = "spaceName") String spaceName) {
		
		log.info("Deleting space {}", spaceName);
		Client client = clientManager.findClientOrThrow(clientName);
		Space space = spaceManager.findSpaceOfClientOrThrow(client, spaceName);
		spaceManager.deleteSpace(space);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
}
