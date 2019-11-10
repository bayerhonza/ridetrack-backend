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
  private DeviceRepository deviceRepository;


  @Test
  @Order(0)
  public void testSensor() {
    // given
    Sensor s1 = new Sensor();
    Device d1 = new Device();
    s1.setId(1);
    d1.setId(2);
    s1.setDevice(d1);
    d1.setSensor(s1);
    s1.setDeviceUid("aze");
    d1.setDeviceUid("aze");
    entityManager.persist(s1);
    entityManager.persist(d1);
    entityManager.flush();
    assertNotEquals(0, s1.getId());
    assertNotEquals(0, d1.getId());
    System.out.println("Sensor id : "+s1.getId()+" Device id : "+d1.getId());

    Sensor s1fresh = sensorRepository.findById(1);
    Device d1fresh = deviceRepository.findById(2);
    assertSame(d1fresh,d1);
    assertSame(s1fresh,s1);
    assertEquals(1, s1fresh.getId());
    assertEquals(2,d1fresh.getId());
    //when

    //then
  }

}
