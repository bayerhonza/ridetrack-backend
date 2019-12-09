package com.ensimag.ridetrack.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ensimag.ridetrack.models.DeviceGroup;
import com.ensimag.ridetrack.models.Space;
import com.ensimag.ridetrack.repository.DeviceGroupRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class DeviceGroupManager {
	
	private static final String DEFAULT_DEVICE_GROUP_NAME = "default_group";
	
	private final DeviceGroupRepository deviceGroupRepository;
	
	@Autowired
	public DeviceGroupManager(DeviceGroupRepository deviceGroupRepository) {
		this.deviceGroupRepository = deviceGroupRepository;
	}
	
	public DeviceGroup createDefaultDeviceGroup(Space space) {
		return createDeviceGroup(space, DEFAULT_DEVICE_GROUP_NAME);
	}
	
	public DeviceGroup createDeviceGroup(Space space, String name) {
		log.info("Creating group '{} of space '{}@{}'", name, space.getName(), space.getOwner().getClientName());
		DeviceGroup newDeviceGroup = DeviceGroup.builder()
				.name(name)
				.space(space)
				.build();
		return deviceGroupRepository.save(newDeviceGroup);
	}
}
