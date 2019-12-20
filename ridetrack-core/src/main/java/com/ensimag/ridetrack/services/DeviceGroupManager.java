package com.ensimag.ridetrack.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ensimag.ridetrack.auth.acl.AclService;
import com.ensimag.ridetrack.exception.RidetrackNotFoundException;
import com.ensimag.ridetrack.models.DeviceGroup;
import com.ensimag.ridetrack.models.Space;
import com.ensimag.ridetrack.repository.DeviceGroupRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class DeviceGroupManager {
	
	public static final String DEFAULT_DEVICE_GROUP_NAME = "default_group";
	
	public static final String DEFAULT_DEVICE_GROUP_UGROUP_NAME = "rootDevGroupUGroup";
	
	private final DeviceGroupRepository deviceGroupRepository;
	
	@Autowired
	private AclService aclService;
	
	@Autowired
	private DeviceManager deviceManager;
	
	@Autowired
	public DeviceGroupManager(DeviceGroupRepository deviceGroupRepository) {
		this.deviceGroupRepository = deviceGroupRepository;
	}
	
	public DeviceGroup createDefaultDeviceGroup(Space space) {
		return createDeviceGroup(space, getDefaultDeviceGroupName());
	}
	
	@PreAuthorize("hasPermission(#space, T(com.ensimag.ridetrack.privileges.PrivilegeEnum).CAN_CREATE_DEV_GROUP)")
	public DeviceGroup createDeviceGroup(Space space, String name) {
		log.info("Creating group '{} of space '{}@{}'", name, space.getName(), space.getOwner().getClientName());
		DeviceGroup newDeviceGroup = new DeviceGroup().toBuilder()
				.name(name)
				.space(space)
				.build();
		return deviceGroupRepository.save(newDeviceGroup);
	}
	
	@PostAuthorize("hasPermission(returnObject, T(com.ensimag.ridetrack.privileges.PrivilegeEnum).CAN_READ)")
	public DeviceGroup findBySpaceAndName(Space space, String devGroupName) {
		return deviceGroupRepository.findBySpaceAndName(space, devGroupName)
				.orElseThrow(() -> new RidetrackNotFoundException("device group " + devGroupName + "not found"));
	}
	
	public void deleteDeviceGroups(Space space) {
		List<DeviceGroup> deviceGroups = deviceGroupRepository.findAllBySpace(space);
		deviceGroups.forEach(deviceGroup -> {
			deviceManager.deleteDevices(deviceGroup.getDevices());
			aclService.deleteAllEntriesOfOid(deviceGroup);
			deviceGroupRepository.delete(deviceGroup);
		});
	}
	
	public String getDefaultDeviceGroupName() {
		return DEFAULT_DEVICE_GROUP_NAME;
	}
	
}
