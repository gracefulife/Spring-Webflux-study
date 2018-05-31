package com.example.research.profile.v1.profile;

import com.example.research.profile.entity.cache.Profile;
import com.example.research.profile.entity.cache.ProfileRepository;
import com.example.research.profile.entity.command.CreateProfileCommand;
import com.example.research.profile.entity.command.ProfileCommand;
import com.example.research.profile.entity.command.UpdateProfileCommand;
import com.example.research.profile.entity.storage.ProfileEventStoreRepository;
import com.example.research.profile.entity.storage.ProfileStorageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Async
@Service
public class ProfileEventHandler {
  @Autowired ProfileRepository profileRepository;
  @Autowired ProfileStorageRepository profileStorageRepository;
  @Autowired ProfileEventStoreRepository profileEventStoreRepository;

  @Autowired ObjectMapper objectMapper;

  @Async
  @Transactional
  public CompletableFuture<com.example.research.profile.entity.storage.ProfileEvent> save(CreateProfileCommand command) {
    return CompletableFuture.supplyAsync(() -> {
      // TODO chaining 이 나을지, 그냥 소비시켜버릴지.
      profileRepository.save(Profile.from(command)).subscribe();
      profileStorageRepository.save(com.example.research.profile.entity.storage.Profile.from(command));
      Long latestVersion = profileEventStoreRepository
          .findTopByIdentifierOrderByNoDesc(command.getId())
          .map(com.example.research.profile.entity.storage.ProfileEvent::getVersion)
          .orElse(0L);
      String payload = generatePayload(command);

      com.example.research.profile.entity.storage.ProfileEvent profileRawEvent
          = com.example.research.profile.entity.storage.ProfileEvent.from(command, latestVersion + 1, payload);
      return profileEventStoreRepository.save(profileRawEvent);
    });
  }

  private String generatePayload(ProfileCommand event) {
    String payload;
    try {
      payload = objectMapper.writeValueAsString(event);
    } catch (JsonProcessingException e) {
      log.error("[ProfileEventHandler] caught error on generate payload", e);
      payload = e.getMessage();
    }
    return payload;
  }
}
