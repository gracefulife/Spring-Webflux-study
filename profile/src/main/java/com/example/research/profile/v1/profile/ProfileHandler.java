package com.example.research.profile.v1.profile;

import com.example.research.profile.entity.cache.Profile;
import com.example.research.profile.entity.cache.ProfileRepository;
import com.example.research.profile.entity.event.ProfileChangedEvent;
import com.example.research.profile.entity.event.ProfileSavedEvent;
import com.example.research.profile.entity.storage.ProfileStorageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.time.LocalDateTime;
import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Component
public class ProfileHandler {
  @Autowired ApplicationEventPublisher applicationEventPublisher;

  @Autowired ProfileRepository profileRepository;
  @Autowired ProfileStorageRepository profileStorageRepository;

  @NonNull
  public Mono<ServerResponse> fetchProfiles(ServerRequest request) {
    Flux<Profile> profiles = Flux.fromIterable(profileStorageRepository.findAll())
        .flatMap(profile -> profileRepository.save(Profile.from(profile)));
    return ok().contentType(MediaType.APPLICATION_JSON).body(profiles, Profile.class);
  }

  @NonNull
  public Mono<ServerResponse> fetch(ServerRequest request) {
    String id = request.pathVariable("id");
    Mono<Profile> profile = profileRepository.findById(id)
        .onErrorResume(throwable ->
            Mono.just(profileStorageRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("profile not found")))
                .flatMap(storedProfile -> profileRepository.save(Profile.from(storedProfile)))
        );
    return ok().contentType(MediaType.APPLICATION_JSON).body(profile, Profile.class);
  }

  @NonNull
  public Mono<ServerResponse> save(Mono<ProfileSaveRequest> request) {
    Function<ProfileSaveRequest, com.example.research.profile.entity.storage.Profile> mapToProfile
        = com.example.research.profile.entity.storage.Profile::from;

    Function<com.example.research.profile.entity.storage.Profile, Mono<com.example.research.profile.entity.storage.Profile>>
        saveProfile = profile -> {
      log.info("profile is {}", profile);
      com.example.research.profile.entity.storage.Profile storeProfile = profileStorageRepository.save(profile);
      applicationEventPublisher.publishEvent(ProfileSavedEvent.from(storeProfile));
      return Mono.just(storeProfile);
    };

    Function<com.example.research.profile.entity.storage.Profile, ProfileSaveResponse> mapToResponse =
        profile -> new ProfileSaveResponse(profile.getId());

    Mono<ProfileSaveResponse> save = request.flatMap(saveProfile.compose(mapToProfile))
        .map(mapToResponse); // FIXME throw 처리

    return ok().body(save, ProfileSaveResponse.class);
  }

  @NonNull
  public Mono<ServerResponse> update(ServerRequest request) {
    Mono<ProfileSaveRequest> profileSaveRequestMono = request.bodyToMono(ProfileSaveRequest.class);
    com.example.research.profile.entity.storage.Profile existProfile = profileStorageRepository
        .findById(request.pathVariable("id")).orElseThrow(IllegalStateException::new);

    return profileSaveRequestMono.flatMap(requestProfile -> {
      existProfile.setName(
          StringUtils.isEmpty(requestProfile.getName()) ?
              existProfile.getName() : requestProfile.getName()
      );
      existProfile.setAge(requestProfile.getAge() == null ? existProfile.getAge() : requestProfile.getAge());
      existProfile.setSex(requestProfile.getSex() == null ? existProfile.getSex() : requestProfile.getSex());
      existProfile.setUpdatedAt(LocalDateTime.now());
      profileStorageRepository.save(existProfile);

      applicationEventPublisher.publishEvent(ProfileChangedEvent.from(existProfile));

      return ok().contentType(MediaType.APPLICATION_JSON).body(fromObject(existProfile))
          .switchIfEmpty(ServerResponse.badRequest().build());
    });
  }
}
