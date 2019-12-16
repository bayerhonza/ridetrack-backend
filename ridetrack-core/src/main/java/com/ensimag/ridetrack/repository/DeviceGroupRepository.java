package com.ensimag.ridetrack.repository;

import java.util.Optional;

import com.ensimag.ridetrack.models.DeviceGroup;
import com.ensimag.ridetrack.models.Space;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceGroupRepository extends JpaRepository<DeviceGroup, Long> {

	Optional<DeviceGroup> findBySpaceAndName(Space space, String devGroupName);
}
