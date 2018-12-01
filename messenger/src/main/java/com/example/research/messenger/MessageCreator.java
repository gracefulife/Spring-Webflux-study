package com.example.research.messenger;

import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TextMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class MessageCreator {
  private final String to;

  public MessageCreator(@Value("${line.user.id}") String to) {
    this.to = to;
  }

  public Mono<PushMessage> createTextMessage(String message) {
    return Mono.just(new PushMessage(to, new TextMessage(message)));
  }
}
