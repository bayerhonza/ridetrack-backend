package com.ensimag.ridetrack.services;

import com.ensimag.ridetrack.repository.DeviceGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("test for DrviceGroupeManager")
public class DeviceGroupeManagerTest {
    @Mock
    DeviceGroupRepository deviceGroupRepository;

    @InjectMocks
    DeviceGroupManager deviceGroupManager;

    @Test
    public void createDefaultGroupe(){

    }

}
