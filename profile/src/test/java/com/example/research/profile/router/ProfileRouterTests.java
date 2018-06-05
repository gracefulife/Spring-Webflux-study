package com.example.research.profile.router;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = TestApplicationContext.class)
@ActiveProfiles("test")
public class ProfileRouterTests {
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
    ProfileSaveRequest profileSaveRequest = generate();
    assertNotNull(profileSaveRequest, "profile request must not be null !");

    webClient
        .post()
        .uri("/api/v1/profiles")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromObject(profileSaveRequest))
        .exchange()
        .expectStatus().isNoContent();
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

  @Test
  public void c_프로필_업데이트() {
    ProfileSaveRequest profileSaveRequest = generate();
    webClient
        .post()
        .uri("/api/v1/profiles")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromObject(profileSaveRequest))
        .exchange()
        .expectStatus().isNoContent();

    profileSaveRequest.setName(profileSaveRequest.getName().toUpperCase());

    webClient
        .put()
        .uri("/api/v1/profiles/" + profileSaveRequest.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromObject(profileSaveRequest))
        .exchange()
        .expectStatus().isNoContent()
        .expectBody()
        .jsonPath("$.name").isEqualTo(profileSaveRequest.getName());
  }

  private ProfileSaveRequest generate() {
    return new ProfileSaveRequest(
        UUID.randomUUID().toString(),
        RandomData.name(), RandomData.randomInteger(1, 80),
        RandomData.randomBoolean() ? "man" : "woman"
    );
  }
}
