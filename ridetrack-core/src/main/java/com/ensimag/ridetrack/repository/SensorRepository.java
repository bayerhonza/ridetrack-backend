package com.ensimag.ridetrack.repository;

import com.ensimag.ridetrack.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {
    Sensor findById(long id_sensor);
}
