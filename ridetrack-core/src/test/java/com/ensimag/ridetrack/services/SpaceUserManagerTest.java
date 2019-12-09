package com.ensimag.ridetrack.services;


import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.stream.Collectors;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ensimag.ridetrack.exception.RidetrackConflictException;
import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.models.DeviceGroup;
import com.ensimag.ridetrack.models.Space;
import com.ensimag.ridetrack.repository.SpaceRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testing class for spaceManager")
class SpaceManagerTest {

	@Mock
	SpaceRepository spaceRepository;

	@Mock
	DeviceGroupManager deviceGroupManager;

	@InjectMocks
	SpaceManager spaceManager;

	@Test
	@DisplayName("create default space for client")
	public void testCreateDefaultSpace() {
		DeviceGroup deviceGroup = new DeviceGroup();
		Mockito.when(spaceRepository.findByOwnerAndName(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(Optional.empty());
		Mockito.when(deviceGroupManager.createDefaultDeviceGroup(ArgumentMatchers.any())).thenReturn(deviceGroup);
		
		Client newClient = Client.builder()
				.clientName("testClient")
				.fullName("Test Client")
				.build();
		Space defaultSpace = spaceManager.createDefaultSpaceForClient(newClient);
		Assertions.assertEquals(SpaceManager.DEFAULT_SPACE_NAME, defaultSpace.getName());
	}

	@Test
	@DisplayName("testing creation of space from name")
	public void testCreateSpaceFromName() {
		DeviceGroup deviceGroup = new DeviceGroup();
		deviceGroup.setName("test_device_group");
		Mockito.when(spaceRepository.findByOwnerAndName(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(Optional.empty());
		Mockito.when(deviceGroupManager.createDefaultDeviceGroup(ArgumentMatchers.any())).thenReturn(deviceGroup);
		
		Client newClient = new Client().toBuilder()
				.clientName("testClient2")
				.fullName("Test Client 2")
				.build();
		Space newSpace = spaceManager.createSpace(newClient, "testing-space1");
		Assertions.assertEquals("testing-space1", newSpace.getName());
		Assertions.assertEquals(newClient, newSpace.getOwner());
		MatcherAssert.assertThat(
			newSpace.getDeviceGroups().stream()
				.map(DeviceGroup::getName)
				.collect(Collectors.toSet()),
			contains("test_device_group"));
	}

	@Test
	@DisplayName("testing creation of space from Space object")
	public void testCreateSpaceFromSpace() {
		DeviceGroup deviceGroup = new DeviceGroup();
		deviceGroup.setName("test_device_group3");
		Mockito.when(deviceGroupManager.createDefaultDeviceGroup(ArgumentMatchers.any())).thenReturn(deviceGroup);
		
		Client newClient = new Client().toBuilder()
				.clientName("testClient2")
				.fullName("Test Client 2")
				.build();
		Space newSpace = new Space().toBuilder()
				.name("test3space")
				.build();
		assertTrue(newSpace.getDeviceGroups().isEmpty());
		spaceManager.createSpace(newClient, newSpace);
		Assertions.assertEquals("test3space", newSpace.getName());
		Assertions.assertEquals(newClient, newSpace.getOwner());
		MatcherAssert.assertThat(
				newSpace.getDeviceGroups().stream()
						.map(DeviceGroup::getName)
						.collect(Collectors.toSet()),
				contains("test_device_group3"));
	}

	@Test
	@DisplayName("testing creation of a space with existing name, exception expected")
	public void testCreateExistingSpace() {
		Mockito.when(spaceRepository.findByOwnerAndName(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(Optional.of(new Space()));
		
		Assertions.assertThrows(RidetrackConflictException.class, () -> spaceManager.createSpace(new Client(), "shall not pass"));
	}

}