package com.example.research.messenger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import reactor.test.StepVerifier;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessengerApplication.class)
@ActiveProfiles("local")
public class MessengerCreatorTests {
  // FIXTURE
  @Autowired MessageCreator messageCreator;

  @Test
  public void publish_test_message() {
    StepVerifier.create(messageCreator.createTextMessage("testMessage"))
        .expectNextCount(1)
        .expectComplete()
        .log()
        .verify();
  }

  @Test
  public void publish_test_likert_message() {
    StepVerifier.create(
        messageCreator.createLikertScaleMessage(
            "testQuestion", "testMessage",
            new LikertScale("strongDisagree", "disagree", "neither", "agree", "stronglyAgree")
        ))
        .expectNextCount(1)
        .expectComplete()
        .log()
        .verify();
  }
}
