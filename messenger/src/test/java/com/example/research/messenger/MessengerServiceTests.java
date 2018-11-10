package com.example.research.messenger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

  @Value("${line.user.id}") String to;
  String message;

  @Before
  public void setUp() {
    message = "테스트 메세지입니다.";
  }

  @Test
  public void publish_test_message() {
    StepVerifier.create(messengerService.publish(to, message))
        .expectComplete()
        .log()
        .verify();
  }
}
