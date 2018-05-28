package com.example.research.profile.v1.profile;

import com.example.research.profile.entity.cache.Profile;
import com.example.research.profile.entity.cache.ProfileRepository;
import com.example.research.profile.entity.storage.ProfileStorageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProfileHandler {
  @Autowired ProfileRepository profileRepository;
  @Autowired ProfileStorageRepository profileStorageRepository;

  @NonNull
  public Mono<ServerResponse> fetchProfiles(ServerRequest request) {
    // send Event
    Flux<Profile> profiles = profileRepository.findAll()
        .switchIfEmpty(
            Flux.fromIterable(profileStorageRepository.findAll())
                .map(Profile::from) // TODO send Event
        );

    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(profiles, Profile.class);
  }

  @NonNull
  public Mono<ServerResponse> fetch(ServerRequest request) {
    String id = request.pathVariable("id");
    Mono<Profile> profile = profileRepository.findById(id)
        .switchIfEmpty(
            Mono.just(
                profileStorageRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("profile not found"))
            )
                .map(Profile::from) // TODO send Event
        );
    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(profile, Profile.class);
  }
}
