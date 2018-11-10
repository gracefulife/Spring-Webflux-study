package com.example.research.messenger;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TextMessage;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@Service
public class MessengerService {
  private final LineMessagingClient lineMessagingClient;

  public Mono<Void> publish(String to, String message) {
    return Mono.fromCompletionStage(
        lineMessagingClient.pushMessage(new PushMessage(to, new TextMessage(message)))
    ).flatMap(botApiResponse -> Mono.empty());
  }
}
