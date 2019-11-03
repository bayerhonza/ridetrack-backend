package com.ensimag.ridetrack.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.ensimag.ridetrack.models.Client;
import com.ensimag.ridetrack.models.Space;
import com.ensimag.ridetrack.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
public class UserRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private UserRepository userRepository;


  @Test
  @Order(0)
  void injectedComponentsAreNotNull(){
    assertThat(dataSource).isNotNull();
    assertThat(jdbcTemplate).isNotNull();
    assertThat(entityManager).isNotNull();
    assertThat(userRepository).isNotNull();
  }

  @Test
  @DisplayName("test Spring Data for Client")
  @Order(1)
  public void testClient() {
    User u1 = new User();
    u1.setName("test1");
    u1.setUsername("username1");
    u1.setHashPassword("abcde123456&é-è_è-('");
    u1.setSurname("surname1");
    userRepository.save(u1);
    entityManager.flush();
    assertNotEquals(0, u1.getId());
    System.out.print(u1.getId());

    User u1fresh = userRepository.findByUsername("username1");
    assertNotNull(u1fresh);
    assertSame(u1fresh, u1);
    assertEquals("test1", u1fresh.getName());
  }

  @Test
  @DisplayName("Test of Client and Space")
  @Order(1)
  public void testInsert() {
    Client cl1 = new Client();
    cl1.setClientName("clien1");
    cl1.setFullName("Client One");

    User u1 = User.builder()
        .name("user1")
        .email("test.test@test.zeA")
        .hashPassword("hashqsdfqsdfhash")
        .surname("surname1")
        .username("username1")
        .build();

    User u2 = User.builder()
        .name("user2")
        .email("a.test@test.za")
        .hashPassword("hashhash22")
        .surname("surname2")
        .username("username2")
        .build();

    Space space = Space.builder()
        .name("space1")
        .owner(cl1)
        .build();
    space.getUsers().add(u1);
    space.getUsers().add(u2);
  }

}