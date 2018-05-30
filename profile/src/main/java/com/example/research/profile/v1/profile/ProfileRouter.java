package com.example.research.profile.v1.profile;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class ProfileRouter {
  @Bean RouterFunction<ServerResponse> apis(ProfileHandler handler) {
    return RouterFunctions
        .route(GET("/api/v1/profiles").and(accept(MediaType.APPLICATION_JSON)),
            handler::fetchProfiles)
        .andRoute(POST("/api/v1/profiles").and(accept(MediaType.APPLICATION_JSON)),
            request -> handler.save(request.bodyToMono(ProfileSaveRequest.class)))
        .andRoute(PUT("/api/v1/profiles/{id}").and(accept(MediaType.APPLICATION_JSON)),
            handler::update)
        .andRoute(GET("/api/v1/profiles/{id}").and(accept(MediaType.APPLICATION_JSON)),
            handler::fetch);
  }
}
