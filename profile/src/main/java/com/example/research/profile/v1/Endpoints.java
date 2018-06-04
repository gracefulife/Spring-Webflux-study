package com.example.research.profile.v1;


import com.example.research.profile.v1.profile.ProfileHandler;
import com.example.research.profile.v1.profile.ProfileSaveRequest;
import com.example.research.profile.v1.tag.TagHandler;
import com.example.research.profile.v1.tag.TagSaveRequest;

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
public class Endpoints {
  @Bean RouterFunction<ServerResponse> apis(ProfileHandler profileHandler, TagHandler tagHandler) {
    return RouterFunctions
        .route(GET("/api/v1/profiles").and(accept(MediaType.APPLICATION_JSON)),
            profileHandler::fetchProfiles)
        .andRoute(POST("/api/v1/profiles").and(accept(MediaType.APPLICATION_JSON)),
            request -> profileHandler.save(request.bodyToMono(ProfileSaveRequest.class)))
        .andRoute(PUT("/api/v1/profiles/{id}").and(accept(MediaType.APPLICATION_JSON)),
            profileHandler::update)
        .andRoute(GET("/api/v1/profiles/{id}").and(accept(MediaType.APPLICATION_JSON)),
            profileHandler::fetch)

        .andRoute(GET("/api/v1/tags").and(accept(MediaType.APPLICATION_JSON)),
            tagHandler::fetchTags)
        .andRoute(POST("/api/v1/tags").and(accept(MediaType.APPLICATION_JSON)),
            request -> tagHandler.save(request.bodyToMono(TagSaveRequest.class)))
        .andRoute(GET("/api/v1/tags/{no}").and(accept(MediaType.APPLICATION_JSON)),
            tagHandler::fetchTag);
  }
}
