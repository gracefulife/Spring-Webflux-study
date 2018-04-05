package com.opensurvey.sample;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class SurveyHandler {
  public Mono<ServerResponse> fetchAll(ServerRequest request) {
    Flux<Survey> people = createMock();
    return ServerResponse.ok().contentType(APPLICATION_JSON).body(people, Survey.class);
  }


  private static Flux<Survey> createMock() {
    return Flux.just(new Survey(1L, "id"), new Survey(2L, "id2"), new Survey(3L, "id3"), new Survey(4L, "id4"));
  }
}
