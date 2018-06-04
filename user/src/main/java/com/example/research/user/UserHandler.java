package com.example.research.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Component
public class UserHandler {
  @Autowired UserRepository userRepository;
  @Autowired ProfileClient profileClient;

  public Mono<ServerResponse> fetchAll(ServerRequest request) {
    return ServerResponse.ok()
        .contentType(APPLICATION_JSON)
        .body(userRepository.findAll().map(UserResponse::from), UserResponse.class);
  }

  public Mono<ServerResponse> fetch(Mono<String> request) {
    return ServerResponse.ok()
        .contentType(APPLICATION_JSON)
        .body(request.flatMap(userRequest -> userRepository.findById(request).doOnError(Throwable::printStackTrace))
            .map(UserResponse::from), UserResponse.class);
  }

  public Mono<ServerResponse> save(Mono<UserRequest> request) {
    return ServerResponse.ok()
        .contentType(APPLICATION_JSON)
        .body(
            request.flatMap(
                userRequest -> userRepository.save(User.from(userRequest))
                    .map(user -> {
                      // send event(message bus) or call api
                      log.info("request = {}", user);
                      profileClient.save(ProfileClient.ProfileSaveRequest.from(user));
                      return user;
                    })
            )
                .map(UserResponse::from), UserResponse.class);
  }

  public Mono<ServerResponse> update(ServerRequest request) {
    final Mono<String> path = Mono.just(request.pathVariable("id"));

    return ServerResponse.ok()
        .contentType(APPLICATION_JSON)
        .body(
            userRepository.findById(path)
                .zipWith(request.bodyToMono(UserRequest.class))
                .flatMap(tuples -> {
                  User user = tuples.getT1();
                  UserRequest userRequest = tuples.getT2();

                  user.setCellphone(userRequest.getCellphone());
                  user.setName(userRequest.getName());
                  return userRepository.save(user);
                })
                .map(UserResponse::from), UserResponse.class
        );
  }
}
