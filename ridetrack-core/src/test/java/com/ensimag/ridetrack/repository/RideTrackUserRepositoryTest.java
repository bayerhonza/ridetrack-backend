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

import com.ensimag.ridetrack.models.User;


@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
public class RideTrackUserRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  @DisplayName("Test Spring Data for Client")
  @Order(1)
  public void testUser() {
    User u1 = new User();
    u1.setName("test1");
    u1.setUsername("username1");
    u1.setPassword("abcde123456&é-è_è-('");
    u1.setSurname("surname1");
    entityManager.persist(u1);
    entityManager.flush();
    assertNotEquals(0, u1.getId());
    System.out.print(u1.getId());

    Optional<User> u1FreshOptional = userRepository.findByUsername("username1");
    assertTrue(u1FreshOptional.isPresent());
    assertSame(u1FreshOptional.get(), u1);
    assertEquals("test1", u1FreshOptional.get().getName());
  }

}