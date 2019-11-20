package com.ensimag.ridetrack.services;

import com.ensimag.ridetrack.models.DeviceGroup;
import com.ensimag.ridetrack.models.Space;
import com.ensimag.ridetrack.repository.DeviceGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class DeviceGroupManager {

	private static final String DEFAULT_DEVICE_GROUP_NAME = "default_group";

	private final DeviceGroupRepository deviceGroupRepository;

	@Autowired
	public DeviceGroupManager(DeviceGroupRepository deviceGroupRepository) {
		this.deviceGroupRepository = deviceGroupRepository;
	}

	public DeviceGroup createDefaultDeviceGroup(Space space) {
		DeviceGroup newDeviceGroup = DeviceGroup.builder()
			.name(DEFAULT_DEVICE_GROUP_NAME)
			.space(space)
			.build();
		deviceGroupRepository.save(newDeviceGroup);
		return newDeviceGroup;
	}
}
