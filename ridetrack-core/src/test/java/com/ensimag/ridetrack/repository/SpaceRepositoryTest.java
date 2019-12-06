package com.ensimag.ridetrack.repository;

import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.models.Space;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class SpaceRepositoryTest extends AbstractRepositoryTest {

	@Autowired
	private SpaceRepository spaceRepository;

	@Test
	@DisplayName("test creation space empty")
	public void testEmptySpace(){
		Space space = Space.builder()
				.name("my_space")
				.build();
		Assertions.assertNull(space.getOwner(), "No owner for my_space");
	}

	@Test
	@DisplayName("add client to a empty space")
	public void testAddClient(){
		Space space = Space.builder()
				.name("my_space")
				.build();
		Client newClient = Client.builder()
				.clientName("Metropole")
				.fullName("Metropole Grenoble")
				.build();
		space.setOwner(newClient);
	}
	

}
