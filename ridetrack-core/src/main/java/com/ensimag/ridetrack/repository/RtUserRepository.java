package com.ensimag.ridetrack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ensimag.ridetrack.models.RtUser;

@Repository
public interface RtUserRepository extends JpaRepository<RtUser, Long> {

  Optional<RtUser> findByUsername(String username);
}
