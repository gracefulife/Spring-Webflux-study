package com.example.research.messenger;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@Service
public class MessengerService {
  private final LineMessagingClient lineMessagingClient;
  private final MessageCreator messageCreator;

  public Mono<Void> publishTextMessage(String message) {
    return publishMessage(messageCreator.createTextMessage(message));
  }

  public Mono<Void> publishLikertScaleSurveyMessage(String question, String message, LikertScale likertScale) {
    return publishMessage(messageCreator.createLikertScaleMessage(question, message, likertScale));
  }

  private Mono<Void> publishMessage(Mono<PushMessage> pushMessageMono) {
    return pushMessageMono
        .flatMap(pushMessage -> Mono.fromCompletionStage(lineMessagingClient.pushMessage(pushMessage)))
        .flatMap(botApiResponse -> Mono.empty());
  }
}
