package com.ensimag.ridetrack.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ensimag.ridetrack.dto.SpaceUserDTO;
import com.ensimag.ridetrack.models.SpaceUser;

@Mapper(componentModel = "spring")
public interface SpaceUserMapper {
	
	SpaceUserDTO toUserDTO(SpaceUser user);
	
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "space", ignore = true)
	@Mapping(target = "sid", ignore = true)
	@Mapping(target = "sidType", ignore = true)
	@Mapping(target = "userGroups", ignore = true)
	SpaceUser toUser(SpaceUserDTO spaceUserDTO);
}
