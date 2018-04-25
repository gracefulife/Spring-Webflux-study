package com.example.research.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class UserHandler {
  @Autowired UserRepository userRepository;

  public Mono<ServerResponse> fetchAll(ServerRequest request) {
    return ServerResponse.ok()
        .contentType(APPLICATION_JSON)
        .body(userRepository.findAll().map(UserResponse::from), UserResponse.class);
  }

  public Mono<ServerResponse> fetch(Mono<UserRequest> request) {
    return ServerResponse.ok()
        .contentType(APPLICATION_JSON)
        .body(request.flatMap(userRequest -> userRepository.findById(userRequest.getNo()))
            .map(UserResponse::from), UserResponse.class);
  }
}
