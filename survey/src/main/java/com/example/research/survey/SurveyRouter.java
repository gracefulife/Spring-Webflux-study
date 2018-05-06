package com.example.research.survey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class SurveyRouter {

  @Bean RouterFunction<ServerResponse> apis(SurveyHandler handler) {
    return RouterFunctions
        .route(GET("/api/v1/surveys").and(accept(MediaType.APPLICATION_JSON)),
            handler::fetchAll)
        .andRoute(POST("/api/v1/surveys").and(accept(MediaType.APPLICATION_JSON)),
            request -> handler.save(request.bodyToMono(SurveyRequest.class)))
        .andRoute(GET("/api/v1/surveys/{id}").and(accept(MediaType.APPLICATION_JSON)),
            request -> handler.fetch(Mono.just(request.pathVariable("id"))));
  }
}
