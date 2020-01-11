package com.ensimag.ridetrack.rest.controllers;
import static com.ensimag.ridetrack.auth.privileges.PrivilegeEnum.CAN_DELETE;
import static com.ensimag.ridetrack.auth.privileges.PrivilegeEnum.CAN_READ;
import static com.ensimag.ridetrack.auth.privileges.PrivilegeEnum.CAN_UPDATE;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ensimag.ridetrack.auth.acl.AclService;
import com.ensimag.ridetrack.dto.MockGpsDTO;
import com.ensimag.ridetrack.exception.RidetrackNotFoundException;
import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.models.Device;
import com.ensimag.ridetrack.models.DeviceData;
import com.ensimag.ridetrack.models.DeviceGroup;
import com.ensimag.ridetrack.models.Space;
import com.ensimag.ridetrack.models.acl.AclObjectIdentity;
import com.ensimag.ridetrack.models.acl.AclUserGroup;
import com.ensimag.ridetrack.repository.AclUserGroupRepository;
import com.ensimag.ridetrack.repository.DeviceDataRepository;
import com.ensimag.ridetrack.repository.DeviceRepository;
import com.ensimag.ridetrack.repository.SensorRepository;
import com.ensimag.ridetrack.rest.api.RestPaths;
import com.ensimag.ridetrack.services.ClientManager;
import com.ensimag.ridetrack.services.DeviceGroupManager;
import com.ensimag.ridetrack.services.SpaceManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(RestPaths.API_PATH)
@Slf4j
@Transactional
public class MockGpsController {
	
	@Autowired
	private ClientManager clientManager;
	
	@Autowired
	private DeviceGroupManager deviceGroupManager;
	
	@Autowired
	private SpaceManager spaceManager;
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	@Autowired
	private SensorRepository sensorRepository;
	
	@Autowired
	private AclUserGroupRepository userGroupRepository;
	
	@Autowired
	private AclService aclService;
	
	@Autowired
	private DeviceDataRepository deviceDataRepository;
	
	@PostMapping(path = "/mock", consumes = { "multipart/mixed", "multipart/form-data" })
	public ResponseEntity<String> importDeviceGps(
			@RequestParam String clientName,
			@RequestParam String spaceName,
			@RequestParam("file") MultipartFile uploadFile) {
		log.info("File size is {}", uploadFile.getSize());
		Client client = clientManager.findClient(clientName);
		Space space = spaceManager.findSpaceOfClientOrThrow(client, spaceName);
		AclUserGroup userGroup = userGroupRepository.findByName(spaceManager.getSpaceDefaultUGroupName(space))
				.orElseThrow(() -> new RidetrackNotFoundException("user group not found"));
		DeviceGroup deviceGroup = deviceGroupManager.findBySpaceAndName(space, deviceGroupManager.getDefaultDeviceGroupName());
		ObjectMapper objectMapper = new ObjectMapper();
		try (InputStream is = uploadFile.getInputStream()) {
			MockGpsDTO[] values = objectMapper.readValue(is, MockGpsDTO[].class);
			Set<AclObjectIdentity> objects = new HashSet<>();
			for (MockGpsDTO value : values) {
				Optional<Device> deviceOpt = deviceRepository.findByDeviceUid(value.getIdDevice());
				Device device;
				if (deviceOpt.isPresent()) {
					device = deviceOpt.get();
				} else {
					device = new Device();
					device.setDeviceUid(value.getIdDevice());
					device.setDeviceGroup(deviceGroup);
					device.setDeviceType(value.getType());
					device.setName(value.getName());
					device.setStatus(value.getStatus());
					objects.add(device);
					deviceRepository.save(device);
				}
				DeviceData deviceData = new DeviceData();
				deviceData.setDevice(device);
				deviceData.setLongitude(value.getLongitude());
				deviceData.setLatitude(value.getLatitude());
				deviceData.setAltitude(value.getAltitude());
				deviceData.setCreatedAt(value.getTimestamp());
				deviceDataRepository.save(deviceData);
				device.addDeviceData(deviceData);
				deviceRepository.save(device);
			}
			aclService.createEntryForEachOid(objects, userGroup, Set.of(CAN_READ, CAN_DELETE, CAN_UPDATE));
		} catch (IOException ex) {
			log.error("Deserialization failed", ex);
		}
		
		return null;
	}
}
