package com.example.research.profile.v1.profile;

import com.example.research.profile.entity.cache.Profile;
import com.example.research.profile.entity.cache.ProfileRepository;
import com.example.research.profile.entity.command.CreateProfileCommand;
import com.example.research.profile.entity.command.UpdateProfileCommand;
import com.example.research.profile.entity.storage.ProfileStorageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.function.Consumer;
import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
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
    // TODO validation
    Function<ProfileSaveRequest, CreateProfileCommand> requestToCommand = CreateProfileCommand::from;
    Consumer<CreateProfileCommand> sendCommand = command -> applicationEventPublisher.publishEvent(command);

    return request.single().flatMap(profile -> {
      sendCommand.accept(requestToCommand.apply(profile));
      return noContent().build();
    });
  }

  @NonNull
  public Mono<ServerResponse> update(ServerRequest request) {
    Mono<ProfileSaveRequest> profileSaveRequestMono = request.bodyToMono(ProfileSaveRequest.class);

    Function<ProfileSaveRequest, UpdateProfileCommand> requestToCommand = (profileSaveRequest) ->
        UpdateProfileCommand.from(request.pathVariable("id"), profileSaveRequest);
    Consumer<UpdateProfileCommand> sendCommand = command -> applicationEventPublisher.publishEvent(command);

    return profileSaveRequestMono.flatMap(profile -> {
      sendCommand.accept(requestToCommand.apply(profile));
      return noContent().build();
    })
        .switchIfEmpty(ServerResponse.badRequest().build());
  }
}
