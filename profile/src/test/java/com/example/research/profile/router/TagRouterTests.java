package com.example.research.tag.entity.router;

import com.example.research.profile.TestApplicationContext;
import com.example.research.profile.v1.tag.TagSaveRequest;
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

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = TestApplicationContext.class)
@ActiveProfiles("test")
public class TagRouterTests {
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
  public void a_태그_생성() {
    TagSaveRequest tagSaveRequest = generate();
    assertNotNull(tagSaveRequest, "tag request must not be null !");

    webClient
        .post()
        .uri("/api/v1/tags")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromObject(tagSaveRequest))
        .exchange()
        .expectStatus().isNoContent();
  }

  @Test
  public void b_태그_조회() {
    webClient
        .get()
        .uri("/api/v1/tags")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk();
  }

//  @Test
//  public void c_태그_업데이트() {
//    TagSaveRequest tagSaveRequest = generate();
//    webClient
//        .post()
//        .uri("/api/v1/tags")
//        .contentType(MediaType.APPLICATION_JSON)
//        .accept(MediaType.APPLICATION_JSON)
//        .body(BodyInserters.fromObject(tagSaveRequest))
//        .exchange()
//        .expectStatus().isNoContent();
//
//    tagSaveRequest.setName(tagSaveRequest.getName().toUpperCase());
//
//    webClient
//        .put()
//        .uri("/api/v1/tags/" + tagSaveRequest.getId())
//        .contentType(MediaType.APPLICATION_JSON)
//        .accept(MediaType.APPLICATION_JSON)
//        .body(BodyInserters.fromObject(tagSaveRequest))
//        .exchange()
//        .expectStatus().isOk()
//        .expectBody()
//        .jsonPath("$.name").isEqualTo(tagSaveRequest.getName());
//  }

  private TagSaveRequest generate() {
    TagSaveRequest tagSaveRequest = new TagSaveRequest();
    tagSaveRequest.setName(RandomData.name());
    return tagSaveRequest;
  }
}
