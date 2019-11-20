package com.ensimag.ridetrack.mappers;

import com.ensimag.ridetrack.dto.ClientDTO;
import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.models.Space;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ClientMapper {

	@Mapping(target = "spaces", source = "spaces", qualifiedByName = "mapSpacesToNameSet")
	ClientDTO toClientDTO(Client client);

	@Named("mapSpacesToNameSet")
	static Set<String> mapSpacesToNameSet(Set<Space> spaces) {
		return spaces.stream()
			.map(Space::getName)
			.collect(Collectors.toSet());
	}

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	Client toClient(ClientDTO clientDTO);
}
