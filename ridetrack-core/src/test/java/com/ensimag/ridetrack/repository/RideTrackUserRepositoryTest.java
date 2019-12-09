package com.ensimag.ridetrack.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ensimag.ridetrack.models.RtUser;
import com.ensimag.ridetrack.models.SpaceUser;

@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
public class RideTrackUserRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private RtUserRepository rtUserRepository;

  @Test
  @DisplayName("Test Spring Data for Client")
  @Order(1)
  public void testUser() {
    RtUser u1 = new SpaceUser();
    u1.setUsername("username1");
    u1.setPassword("abcde123456&é-è_è-('");
    entityManager.persist(u1);
    entityManager.flush();
    assertNotEquals(0, u1.getUserId());
    System.out.print(u1.getUserId());

    Optional<RtUser> u1FreshOptional = rtUserRepository.findByUsername("username1");
    assertTrue(u1FreshOptional.isPresent());
    assertSame(u1FreshOptional.get(), u1);
    assertEquals("username1", u1FreshOptional.get().getUsername());
  }

}