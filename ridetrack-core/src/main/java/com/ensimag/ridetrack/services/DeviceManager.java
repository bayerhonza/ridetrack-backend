package com.ensimag.ridetrack.services;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ensimag.ridetrack.auth.acl.AclService;
import com.ensimag.ridetrack.models.Device;
import com.ensimag.ridetrack.repository.DeviceDataRepository;
import com.ensimag.ridetrack.repository.DeviceRepository;

@Service
public class DeviceManager {
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	@Autowired
	private DeviceDataRepository deviceDataRepository;
	
	@Autowired
	private AclService aclService;
	
	public void deleteDevices(Set<Device> devices) {
		devices.forEach(device -> {
			deviceDataRepository.deleteAll(device.getDeviceData());
			aclService.deleteAllEntriesOfOid(device);
			deviceRepository.delete(device);
		});
		deviceRepository.deleteAll(devices);
	}
	
}
