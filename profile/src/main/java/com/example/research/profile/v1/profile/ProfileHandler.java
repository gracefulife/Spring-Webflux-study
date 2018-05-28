package com.example.research.profile.v1.profile;

import com.example.research.profile.entity.cache.Profile;
import com.example.research.profile.entity.cache.ProfileRepository;
import com.example.research.profile.entity.event.ProfileSavedEvent;
import com.example.research.profile.entity.storage.ProfileStorageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.function.Function;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class ProfileHandler {
  @Autowired ApplicationEventPublisher applicationEventPublisher;

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

  public Mono<ServerResponse> save(Mono<ProfileSaveRequest> request) {
    Function<ProfileSaveRequest, com.example.research.profile.entity.storage.Profile> mapToProfile
        = com.example.research.profile.entity.storage.Profile::from;

    Function<com.example.research.profile.entity.storage.Profile, Mono<com.example.research.profile.entity.storage.Profile>>
        saveProfile = profile -> {
      com.example.research.profile.entity.storage.Profile storeProfile = profileStorageRepository.save(profile);
      applicationEventPublisher.publishEvent(ProfileSavedEvent.from(storeProfile));
      return Mono.just(storeProfile);
    };

    Function<com.example.research.profile.entity.storage.Profile, ProfileSaveResponse> mapToResponse =
        profile -> new ProfileSaveResponse(profile.getId());

    Mono<ProfileSaveResponse> save = request.flatMap(saveProfile.compose(mapToProfile)).map(mapToResponse); // FIXME throw 처리

    return ServerResponse.ok()
        .contentType(APPLICATION_JSON)
        .body(save, ProfileSaveResponse.class);
  }
}
