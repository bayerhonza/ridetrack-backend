package com.ensimag.ridetrack.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ensimag.ridetrack.dto.ClientDTO;
import com.ensimag.ridetrack.models.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {

	ClientDTO toClientDTO(Client client);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	Client toClient(ClientDTO clientDTO);
}
