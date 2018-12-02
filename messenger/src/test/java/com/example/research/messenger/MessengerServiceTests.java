package com.example.research.messenger;

import org.junit.Before;
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
public class MessengerServiceTests {
  // FIXTURE
  @Autowired MessengerService messengerService;
  private String message;

  private String likertScaleTestQuestion;
  private String likertScaleTestMessage;
  private LikertScale likertScale;


  @Before
  public void setUp() {
    message = "테스트 메세지입니다.";

    likertScaleTestQuestion = "5점 척도 테스트";
    likertScaleTestMessage = "5점 척도 테스트 메세지 내용";
    likertScale = new LikertScale("strongDisagree", "disagree", "neither", "agree", "stronglyAgree");
  }

  @Test
  public void publish_test_message() {
    StepVerifier.create(messengerService.publishTextMessage(message))
        .expectComplete()
        .log()
        .verify();
  }

  @Test
  public void publish_test_likert_message() {
    StepVerifier.create(messengerService.publishLikertScaleSurveyMessage(
        likertScaleTestQuestion, likertScaleTestMessage, likertScale
    ))
        .expectComplete()
        .log()
        .verify();
  }
}
