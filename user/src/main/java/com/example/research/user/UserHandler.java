package com.example.research.user;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class UserHandler {
  public Mono<ServerResponse> fetchAll(ServerRequest request) {
    Flux<UserResponse> peoples = createMock();
    return ServerResponse.ok().contentType(APPLICATION_JSON).body(peoples, UserResponse.class);
  }


  private static Flux<UserResponse> createMock() {
    return Flux.just(new UserResponse(1L, "id", "01025920791"));
  }
}
