package com.ensimag.ridetrack.repository;

import com.ensimag.ridetrack.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {

    Optional<Sensor> findBydeviceUid (String deviceUid);

}
