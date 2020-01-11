package com.ensimag.ridetrack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ensimag.ridetrack.models.Sensor;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {

	Optional<Sensor> findBySensorUid(String uid);
}
