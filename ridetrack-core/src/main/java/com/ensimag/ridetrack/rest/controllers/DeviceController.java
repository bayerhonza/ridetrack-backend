package com.ensimag.ridetrack.rest.controllers;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ensimag.ridetrack.auth.RtUserPrincipal;
import com.ensimag.ridetrack.dto.AllUserDevicesModel;
import com.ensimag.ridetrack.dto.DeviceDataModel;
import com.ensimag.ridetrack.dto.DeviceGroupModel;
import com.ensimag.ridetrack.dto.DeviceModel;
import com.ensimag.ridetrack.exception.RidetrackNotFoundException;
import com.ensimag.ridetrack.models.ClientUser;
import com.ensimag.ridetrack.models.Device;
import com.ensimag.ridetrack.models.DeviceData;
import com.ensimag.ridetrack.models.DeviceGroup;
import com.ensimag.ridetrack.repository.ClientUserRepository;
import com.ensimag.ridetrack.rest.api.RestPaths;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(RestPaths.API_PATH)
@Slf4j
@Transactional
public class DeviceController {
	
	@Autowired
	private ClientUserRepository userRepository;
	
	
	@GetMapping("/client/devices")
	@PreAuthorize("hasRole('CLIENT')")
	public ResponseEntity<AllUserDevicesModel> getUserDevices(
			@AuthenticationPrincipal RtUserPrincipal principal) {
		ClientUser clientUser = userRepository.findByUsername(principal.getUsername())
				.orElseThrow(() -> new RidetrackNotFoundException("Client user not found"));
		Set<DeviceGroup> deviceGroups = clientUser.getAssignedClient().getDefaultSpace().getDeviceGroups();
		AllUserDevicesModel model = AllUserDevicesModel.builder()
				.clientName(clientUser.getAssignedClient().getClientName())
				.deviceGroups(deviceGroups.stream()
						.map(this::mapDeviceGroupToModel)
						.collect(Collectors.toSet()))
				.build();
		return ResponseEntity.ok(model);
	}
	
	@PreAuthorize("hasPermission(#deviceGroup, T(com.ensimag.ridetrack.privileges.PrivilegeEnum).CAN_READ)")
	public DeviceGroupModel mapDeviceGroupToModel(final DeviceGroup deviceGroup) {
		return DeviceGroupModel.builder()
				.name(deviceGroup.getName())
				.devices(deviceGroup.getDevices().stream()
						.map(this::mapDeviceToDeviceModel)
						.collect(Collectors.toSet()))
				.build();
	}
	
	@PreAuthorize("hasPermission(#device, T(com.ensimag.ridetrack.privileges.PrivilegeEnum).CAN_READ)")
	public DeviceModel mapDeviceToDeviceModel(final Device device) {
		return DeviceModel.builder()
				.deviceType(device.getDeviceType())
				.deviceUid(device.getDeviceUid())
				.name(device.getName())
				.data(device.getDeviceData().stream()
						.map(this::mapDeviceDataToModel)
						.collect(Collectors.toSet()))
				.build();
	}
	
	private DeviceDataModel mapDeviceDataToModel(final DeviceData deviceData) {
		return DeviceDataModel.builder()
				.deviceStatus("OK")
				.xCoordinate(deviceData.getLongitude())
				.yCoordinate(deviceData.getLatitude())
				.timestamp(deviceData.getCreatedAt())
				.build();
	}
	
}
