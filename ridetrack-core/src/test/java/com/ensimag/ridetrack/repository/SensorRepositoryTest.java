package com.ensimag.ridetrack.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.ensimag.ridetrack.models.Device;
import com.ensimag.ridetrack.models.Sensor;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
public class SensorRepositoryTest extends AbstractRepositoryTest {
/*
  public interface SensorRepository extends JpaRepository<Sensor>{
    public Sensor findByName(String name);
  }

 */
  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private SensorRepository sensorRepository;

  @Autowired
  private Device justepourvoir;

  @Test
  @Order(0)
  public void testSensor() {
    // given
    Sensor s1 = new Sensor();
    s1.setId(1);
    s1.setDeviceUid("aze");
    s1.setDevice(justepourvoir);
    entityManager.persist(s1);
    entityManager.flush();
    assertNotEquals(0, s1.getId());
    System.out.println(s1.getId());

    Sensor s1fresh = sensorRepository.findByIdSensor(1);
    assertNotEquals(s1fresh);
    assertSame(s1fresh,s1);
    assertEquals("test1", s1fresh.getId());
    //when

    //then
  }

}
