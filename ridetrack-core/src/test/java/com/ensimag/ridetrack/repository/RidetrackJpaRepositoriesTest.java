package com.ensimag.ridetrack.repository;

import com.ensimag.ridetrack.models.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
		entityManager.flush();
		Assertions.assertNotEquals(0,newClient.getId());
	}

	@Test
	@Order(0)
	public void createSpace() {
	}
}
