package com.ensimag.ridetrack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ensimag.ridetrack.models.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

  Optional<Client> findByClientName(String clientName);

  void deleteClientByClientName(String clientName);
}
