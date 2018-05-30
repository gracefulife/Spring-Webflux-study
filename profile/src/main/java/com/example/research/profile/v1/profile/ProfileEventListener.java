package com.example.research.profile.v1.profile;

import com.example.research.profile.entity.cache.Profile;
import com.example.research.profile.entity.cache.ProfileRepository;
import com.example.research.profile.entity.event.ProfileChangedEvent;
import com.example.research.profile.entity.event.ProfileSavedEvent;
import com.example.research.profile.entity.storage.ProfileStorageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * FIXME profile cache 에 event set 을 들고 있을지 결정하여야 함
 */
@Slf4j
@Component
public class ProfileEventListener {
  @Autowired ProfileEventHandler profileEventHandler;
  @Autowired ProfileRepository profileRepository;
  @Autowired ProfileStorageRepository profileStorageRepository;

  @EventListener public void onProfileSavedEventReceived(ProfileSavedEvent event) {
    log.info("onProfileSavedEventReceived received : {}" + event);
    profileRepository.save(Profile.from(event))
        .flatMap(profile -> Mono.fromCompletionStage(profileEventHandler.save(event)))
        .doOnError(e -> log.error("onProfileSavedEventReceived error : {}", e))
        .onErrorResume(throwable -> profileRepository.deleteById(event.getId()).flatMap(aVoid -> Mono.empty()))
        .subscribe();
  }

  @EventListener public void onProfileChangedEventReceived(ProfileChangedEvent event) {
    log.info("onProfileChangedEventReceived received : {}" + event);

    Mono.fromCompletionStage(profileEventHandler.save(event))
        .flatMap(profileEvent -> profileRepository.findById(profileEvent.getIdentifier()))
        .single()
        .flatMap(profile -> profileRepository.save(Profile.from(event)))
        .onErrorResume(throwable -> {
          log.error("onError: ", throwable);
          com.example.research.profile.entity.storage.Profile profile = profileStorageRepository
              .findById(event.getId()).orElseThrow(() -> new IllegalStateException("not found"));
          return profileRepository.save(Profile.from(profile));
        })
        .subscribe();
  }
}
