package com.ensimag.ridetrack.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class SpaceRepositoryTest extends AbstractRepositoryTest {

	@Autowired
	private SpaceRepository spaceRepository;

	@Test
	public void initTest() {

	}
}
