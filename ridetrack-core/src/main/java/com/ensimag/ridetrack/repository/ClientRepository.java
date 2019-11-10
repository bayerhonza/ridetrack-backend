package com.ensimag.ridetrack.repository;

import com.ensimag.ridetrack.models.Client;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

  Optional<Client> findByClientName(String clientName);

  void deleteClientByClientName(String clientName);
}
