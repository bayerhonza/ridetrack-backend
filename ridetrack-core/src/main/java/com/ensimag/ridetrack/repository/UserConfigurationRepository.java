package com.ensimag.ridetrack.repository;

import com.ensimag.ridetrack.models.UserConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserConfigurationRepository extends JpaRepository<UserConfiguration, Long> {

}
