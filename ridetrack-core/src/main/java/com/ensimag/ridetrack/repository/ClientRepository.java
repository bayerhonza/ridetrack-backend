package com.ensimag.ridetrack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import com.ensimag.ridetrack.models.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

  /**
   * Find client by clientName
   * @param clientName clientName
   * @return optional of client
   */
  @PostAuthorize("hasPermission(returnObject,'CAN_READ')")
  Optional<Client> findByClientName(String clientName);

  /**
   * Delete client
   * @param clientName clientName of client to be deleted
   */
  void deleteClientByClientName(String clientName);
}
