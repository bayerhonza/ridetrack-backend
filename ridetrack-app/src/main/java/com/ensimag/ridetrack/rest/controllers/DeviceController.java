package com.ensimag.ridetrack.rest.controllers;

import static com.ensimag.ridetrack.auth.privileges.PrivilegeEnum.CAN_DELETE;
import static com.ensimag.ridetrack.auth.privileges.PrivilegeEnum.CAN_READ;
import static com.ensimag.ridetrack.auth.privileges.PrivilegeEnum.CAN_UPDATE;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ensimag.ridetrack.auth.RtUserPrincipal;
import com.ensimag.ridetrack.auth.acl.AclService;
import com.ensimag.ridetrack.dto.DeviceDTO;
import com.ensimag.ridetrack.dto.DeviceDataModel;
import com.ensimag.ridetrack.dto.DeviceGroupModel;
import com.ensimag.ridetrack.dto.DeviceModel;
import com.ensimag.ridetrack.dto.LastDevicePositionDTO;
import com.ensimag.ridetrack.dto.LastDevicePositionsModel;
import com.ensimag.ridetrack.exception.RidetrackNotFoundException;
import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.models.ClientUser;
import com.ensimag.ridetrack.models.Device;
import com.ensimag.ridetrack.models.DeviceData;
import com.ensimag.ridetrack.models.DeviceGroup;
import com.ensimag.ridetrack.models.Space;
import com.ensimag.ridetrack.models.acl.AclUserGroup;
import com.ensimag.ridetrack.repository.AclUserGroupRepository;
import com.ensimag.ridetrack.repository.ClientUserRepository;
import com.ensimag.ridetrack.repository.DeviceGroupRepository;
import com.ensimag.ridetrack.repository.DeviceRepository;
import com.ensimag.ridetrack.rest.api.RestPaths;
import com.ensimag.ridetrack.services.ClientManager;
import com.ensimag.ridetrack.services.SpaceManager;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(RestPaths.API_PATH)
@Slf4j
@Transactional
public class DeviceController {
	
	@Autowired
	private ClientUserRepository userRepository;
	
	@Autowired
	private DeviceGroupRepository deviceGroupRepository;
	
	@Autowired
	private ClientManager clientManager;
	
	@Autowired
	private SpaceManager spaceManager;
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	@Autowired
	private AclUserGroupRepository userGroupRepository;
	
	@Autowired
	private AclService aclService;
	
	@GetMapping("/client/devices")
	@PreAuthorize("hasRole('CLIENT')")
	public ResponseEntity<LastDevicePositionsModel> getDevicesLastPosition(
			@AuthenticationPrincipal RtUserPrincipal principal) {
		ClientUser clientUser = userRepository.findByUsername(principal.getUsername())
				.orElseThrow(() -> new RidetrackNotFoundException("Client user not found"));
		Set<DeviceGroup> deviceGroups = clientUser.getAssignedClient().getDefaultSpace().getDeviceGroups();
		LastDevicePositionsModel model = LastDevicePositionsModel.builder()
				.clientName(clientUser.getAssignedClient().getClientName())
				.deviceGroups(deviceGroups.stream()
						.map(this::mapDeviceGroupToModel)
						.collect(Collectors.toSet()))
				.build();
		return ResponseEntity.ok(model);
	}
	
	@GetMapping("/client/device")
	@PreAuthorize("hasRole('CLIENT')")
	public ResponseEntity<DeviceModel> getDeviceData(@RequestParam(name = "idDevice") String idDevice,
			@RequestParam(name = "limit") Integer limit) {
		Device device = deviceRepository.findByDeviceUid(idDevice)
				.orElseThrow(() -> new RidetrackNotFoundException("Device user not found"));
		return ResponseEntity.ok(mapDeviceToDeviceModel(device, limit));
	}
	
	@PutMapping("/client/device")
	@PreAuthorize("hasRole('CLIENT')")
	public void createDevice(@Valid @RequestBody DeviceDTO deviceDTO,
			@RequestParam String clientName,
			@RequestParam String spaceName,
			@RequestParam String deviceGroupName) {
		Client client = clientManager.findClient(clientName);
		Space space = spaceManager.findSpaceOfClientOrThrow(client, spaceName);
		DeviceGroup deviceGroup = deviceGroupRepository.findBySpaceAndName(space,deviceGroupName)
				.orElseThrow(() -> new RidetrackNotFoundException("Device group not found"));
		
		Device device = new Device();
		device.setStatus(deviceDTO.getStatus());
		device.setDeviceType(deviceDTO.getDeviceType());
		device.setDeviceUid(deviceDTO.getDeviceUid());
		device.setDeviceGroup(deviceGroup);
		device.setStatus(deviceDTO.getStatus());
		device.setName(deviceDTO.getName());
		deviceRepository.save(device);
		AclUserGroup userGroup = userGroupRepository.findByName(spaceManager.getSpaceDefaultUGroupName(space))
				.orElseThrow(() -> new RidetrackNotFoundException("user group not found"));
		aclService.createEntryForEachOid(Set.of(device), userGroup, Set.of(CAN_READ, CAN_DELETE, CAN_UPDATE));
		
	}
	
	@PreAuthorize("hasPermission(#deviceGroup, T(com.ensimag.ridetrack.auth.privileges.PrivilegeEnum).CAN_READ)")
	public DeviceGroupModel mapDeviceGroupToModel(final DeviceGroup deviceGroup) {
		return DeviceGroupModel.builder()
				.name(deviceGroup.getName())
				.devices(deviceGroup.getDevices().stream()
						.map(this::mapDeviceToLastDevicePositionModel)
						.collect(Collectors.toList()))
				.build();
	}
	
	@PreAuthorize("hasPermission(#device, T(com.ensimag.ridetrack.auth.privileges.PrivilegeEnum).CAN_READ)")
	public DeviceModel mapDeviceToDeviceModel(final Device device, Integer limit) {
		return DeviceModel.builder()
				.deviceType(device.getDeviceType())
				.deviceUid(device.getDeviceUid())
				.name(device.getName())
				.data(device.getDeviceData().stream()
						.skip(Math.max(0, device.getDeviceData().size() - limit))
						.map(this::mapDeviceDataToModel)
						.collect(Collectors.toList()))
				.build();
	}
	
	@PreAuthorize("hasPermission(#device, T(com.ensimag.ridetrack.auth.privileges.PrivilegeEnum).CAN_READ)")
	public LastDevicePositionDTO mapDeviceToLastDevicePositionModel(final Device device) {
		return LastDevicePositionDTO.builder()
				.deviceType(device.getDeviceType())
				.deviceUid(device.getDeviceUid())
				.name(device.getName())
				.lastPosition(mapDeviceDataToModel(device.getDeviceData().get(device.getDeviceData().size() - 1)))
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
