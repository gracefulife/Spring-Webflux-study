package com.example.research.survey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class SurveyHandler {
  @Autowired SurveyRepository surveyRepository;

  // FIXME not yet implements
  public Mono<ServerResponse> fetchAll(ServerRequest serverRequest) {
    return null;
  }

  // FIXME not yet implements
  public Mono<ServerResponse> fetch(Mono<String> id) {
    return null;
  }

  // FIXME not yet implements
  public Mono<ServerResponse> save(Mono<SurveyRequest> request) {
    return null;
  }
}
