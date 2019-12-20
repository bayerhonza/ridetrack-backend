package com.ensimag.ridetrack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ensimag.ridetrack.models.DeviceGroup;
import com.ensimag.ridetrack.models.Space;

@Repository
public interface DeviceGroupRepository extends JpaRepository<DeviceGroup, Long> {
	
	Optional<DeviceGroup> findBySpaceAndName(Space space, String devGroupName);
}
