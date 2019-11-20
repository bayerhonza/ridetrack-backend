package com.ensimag.ridetrack.mappers;

import com.ensimag.ridetrack.dto.SpaceDTO;
import com.ensimag.ridetrack.models.Space;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SpaceMapper {

	@Mapping(target = "clientName", source = "owner.clientName")
	SpaceDTO toSpaceDTO(Space space);


}
