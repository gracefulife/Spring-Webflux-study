package com.example.research.profile.entity.router;

import com.example.research.profile.TestApplicationContext;
import com.example.research.profile.v1.profile.ProfileSaveRequest;
import com.namics.commons.random.RandomData;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = TestApplicationContext.class)
public class UserApplicationTests {
  @Autowired ApplicationContext applicationContext;

  private WebTestClient webClient;

  @Before
  public void setup() {
    webClient = WebTestClient.bindToApplicationContext(applicationContext)
        .configureClient()
        .baseUrl("http://localhost:8080/")
        .build();
  }

  @Test
  public void a_프로필_생성() {
    ProfileSaveRequest profileSaveRequest = new ProfileSaveRequest(
        RandomData.randomString(),
        RandomData.name(), RandomData.randomInteger(1, 80),
        RandomData.randomBoolean() ? "man" : "woman"
    );
    assertNotNull(profileSaveRequest, "profile request must not be null !");

    webClient
        .post()
        .uri("/api/v1/profiles")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromObject(profileSaveRequest))
        .exchange()
        .expectStatus().isOk();
  }

  @Test
  public void b_프로필_조회() {
    webClient
        .get()
        .uri("/api/v1/profiles")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk();
  }
}
