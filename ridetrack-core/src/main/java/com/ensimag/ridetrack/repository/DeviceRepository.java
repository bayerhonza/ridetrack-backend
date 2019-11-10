package com.ensimag.ridetrack.repository;

import com.ensimag.ridetrack.models.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device findById(long id_device);
}
