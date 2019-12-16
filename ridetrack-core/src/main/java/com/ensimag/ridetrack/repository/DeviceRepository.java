package com.ensimag.ridetrack.repository;

import java.util.Optional;

import com.ensimag.ridetrack.models.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
	
	Optional<Device> findByDeviceUid(String name);

}
