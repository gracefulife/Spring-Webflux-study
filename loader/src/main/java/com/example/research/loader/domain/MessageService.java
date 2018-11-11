package com.example.research.loader.domain;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@Service
public class MessageService {
  private final MessageRepository messageRepository;

  public Mono<Message> save(MessageRequest messageRequestMono) {
    return Mono.just(messageRequestMono).map(MessageRequest::to)
        .flatMap(messageRepository::save);
  }
}
