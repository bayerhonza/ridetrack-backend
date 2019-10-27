package com.ensimag.ridetrack.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.ensimag.ridetrack.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


@DataJpaTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  public void testInsert() {
    User u1 = new User();
    u1.setName("test1");
    u1.setUsername("username1");
    userRepository.save(u1);
    assertNotEquals(0, u1.getId());
    System.out.print(u1.getId());
  }

  @Test
  public void testGetData() {
    User u1fresh = userRepository.findByUsername("username1");
    assertEquals("test1", u1fresh.getName());
  }
}