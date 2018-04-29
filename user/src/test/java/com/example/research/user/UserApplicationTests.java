package com.example.research.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserApplicationTests {
  @Autowired WebTestClient webClient;

  @Test
  public void testFindAll() {
    this.webClient.get().uri("/").accept(MediaType.APPLICATION_JSON).exchange()
        .expectStatus().isOk();
  }

  @Test
  public void testActuatorStatus() {
    this.webClient.get().uri("/actuator").accept(MediaType.APPLICATION_JSON)
        .exchange().expectStatus().isOk();
  }
}
