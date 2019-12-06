package com.ensimag.ridetrack.services;

import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.models.DeviceGroup;
import com.ensimag.ridetrack.models.Space;
import com.ensimag.ridetrack.repository.DeviceGroupRepository;
import com.ensimag.ridetrack.repository.SpaceRepository;
import jdk.security.jarsigner.JarSigner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DisplayName("test for DrviceGroupManager")
public class DeviceGroupManagerTest {
    @Mock
    DeviceGroupRepository deviceGroupRepository;

    @Mock
    SpaceManager spaceManager;

    @Mock
    SpaceRepository spaceRepository;

    @InjectMocks
    DeviceGroupManager deviceGroupManager;

    @Test
    public void createDefaultGroup() {
        Space newSpace = new Space();
        DeviceGroup deviceGroup = new DeviceGroup();
        Client newClient = new Client().toBuilder()
                .clientName("Metropole")
                .fullName("TGrenoble Metropole")
                .build();
        Mockito.when(deviceGroupRepository.findBySpaceAndName(ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(Optional.empty());
        Space newsSpace = Space.builder()
                .name("Metropolis")
                .owner(newClient)
                .build();
        Mockito.when(spaceManager.createSpace(newClient,"Metropolis"));
        Assertions.assertEquals(deviceGroupManager.DEFAULT_DEVICE_GROUP_NAME, deviceGroup.getName());

    }
}