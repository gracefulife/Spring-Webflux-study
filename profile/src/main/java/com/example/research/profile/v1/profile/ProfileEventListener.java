package com.example.research.profile.v1.profile;

import com.example.research.profile.entity.cache.ProfileRepository;
import com.example.research.profile.entity.command.CreateProfileCommand;
import com.example.research.profile.entity.command.UpdateProfileCommand;
import com.example.research.profile.entity.storage.ProfileStorageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ProfileEventListener {
  @Autowired ProfileEventHandler profileEventHandler;
  @Autowired ProfileRepository profileRepository;
  @Autowired ProfileStorageRepository profileStorageRepository;

  @EventListener public void onProfileSavedEventReceived(CreateProfileCommand command) {
    log.info("onProfileSavedEventReceived received : {}" + command);
    Mono.fromCompletionStage(profileEventHandler.save(command))
        .doOnError(e -> log.error("onProfileSavedEventReceived error : {}", e))
        .onErrorResume(throwable -> profileRepository.deleteById(command.getId()).flatMap(aVoid -> Mono.empty()))
        .subscribe();
  }

  // FIXME subscribe 시 memory 누수가 발생하지 않는지 관찰해야함. (dispose 하지 않아도, 될 것 같은데)
  @EventListener public void onProfileChangedEventReceived(UpdateProfileCommand event) {
    log.info("onProfileChangedEventReceived received : {}" + event);

    Mono.fromCompletionStage(profileEventHandler.update(event))
        .flatMap(profileEvent -> profileRepository.findById(profileEvent.getIdentifier()))
        .doOnError(e -> log.error("onProfileSavedEventReceived error : {}", e))
        .subscribe();
  }
}
