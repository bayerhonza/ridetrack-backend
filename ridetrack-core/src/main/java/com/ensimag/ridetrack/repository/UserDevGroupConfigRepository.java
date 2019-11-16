package com.ensimag.ridetrack.repository;

import com.ensimag.ridetrack.models.UserDevGroupConfig;
import com.ensimag.ridetrack.models.UserDevGroupConfigPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDevGroupConfigRepository extends JpaRepository<UserDevGroupConfig, UserDevGroupConfigPK> {

}
