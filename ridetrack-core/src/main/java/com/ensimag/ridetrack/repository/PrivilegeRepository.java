package com.ensimag.ridetrack.repository;

import com.ensimag.ridetrack.models.Privilege;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

	Optional<Privilege> findByOperationName(String operationName);
}
