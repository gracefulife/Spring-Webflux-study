package com.example.research.messenger;

import com.linecorp.bot.client.LineMessagingClient;

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
    return messageCreator.createTextMessage(message)
        .flatMap(pushMessage -> Mono.fromCompletionStage(lineMessagingClient.pushMessage(pushMessage)))
        .flatMap(botApiResponse -> Mono.empty());
  }
}
