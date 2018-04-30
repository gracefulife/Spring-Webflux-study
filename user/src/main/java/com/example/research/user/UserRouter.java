package com.example.research.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class UserRouter {
  @Bean RouterFunction<ServerResponse> apis(UserHandler handler) {
    return RouterFunctions
        .route(GET("/api/v1/users").and(accept(MediaType.APPLICATION_JSON)),
            handler::fetchAll)
        .andRoute(POST("/api/v1/users").and(accept(MediaType.APPLICATION_JSON)),
            request -> handler.save(request.bodyToMono(UserRequest.class)))
        .andRoute(GET("/api/v1/users/{id}").and(accept(MediaType.APPLICATION_JSON)),
            request -> handler.fetch(Mono.just(request.pathVariable("id"))))
        .andRoute(PUT("/api/v1/users/{id}").and(accept(MediaType.APPLICATION_JSON)),
            handler::update
        );
  }
}
