package com.ensimag.ridetrack.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ensimag.ridetrack.models.DeviceData;

@Repository
public interface DeviceDataRepository extends JpaRepository<DeviceData, Long> {
	
}
