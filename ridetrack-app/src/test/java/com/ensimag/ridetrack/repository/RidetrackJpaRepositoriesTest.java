package com.ensimag.ridetrack.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.models.Space;

@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
public class RidetrackJpaRepositoriesTest  extends AbstractRepositoryTest{
	@Autowired
	private ClientRepository clientRepository;

	@Test
	@Order(0)
	public void createClientTest() {
		Client newClient = Client.builder()
				.clientName("testClientName")
				.fullName("Full Client Name")
				.build();
		entityManager.persist(newClient);
		assertNotNull(newClient.getOid());
		Space newSpace = new Space().toBuilder()
				.name("testSpace")
				.owner(newClient)
				.build();
		entityManager.persist(newSpace);
		assertNotNull(newSpace.getOid());
		clientRepository.delete(newClient);
		assertFalse(clientRepository.findByClientName(newClient.getClientName()).isPresent());
	}
	
}
