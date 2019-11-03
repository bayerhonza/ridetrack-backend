package com.ensimag.ridetrack.repository;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
public class SensorRepositoryTest extends AbstractRepositoryTest {

  @Test
  public void testSensor() {

  }
}
