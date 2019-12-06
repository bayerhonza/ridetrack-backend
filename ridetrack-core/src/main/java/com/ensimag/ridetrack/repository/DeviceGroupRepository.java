package com.ensimag.ridetrack.repository;

import com.ensimag.ridetrack.models.DeviceGroup;
import com.ensimag.ridetrack.models.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceGroupRepository extends JpaRepository<DeviceGroup, Long> {

    Optional<DeviceGroup> findBySpaceAndName(Space space, String groupName);

}
