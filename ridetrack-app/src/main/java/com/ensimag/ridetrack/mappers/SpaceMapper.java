package com.ensimag.ridetrack.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ensimag.ridetrack.dto.SpaceDTO;
import com.ensimag.ridetrack.models.Space;

@Mapper(componentModel = "spring")
public interface SpaceMapper {

	@Mapping(target = "clientName", source = "owner.clientName")
	SpaceDTO toSpaceDTO(Space space);
	
	
	@Mapping(target = "name", source = "name")
	@Mapping(target = "owner", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "oid", ignore = true)
	Space toSpace(SpaceDTO spaceDTO);


}
