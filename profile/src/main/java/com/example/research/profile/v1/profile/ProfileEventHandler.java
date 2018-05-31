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
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import lombok.extern.slf4j.Slf4j;

/**
 * 입력에 대해서는 non-blocking 을 포기한다 . ㅎㅎ
 * jdbc 이녀석.
 */
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

  // FIXME CompletableFuture 에서 Void 객체를 response 시키고 싶은데, Mono.empty 로 발행되면 체이닝이 안되니, 보통 이런 경우에 어떻게 사용하는지 리서치를 해보아야 함
  @Async
  @Transactional
  public CompletableFuture<com.example.research.profile.entity.storage.ProfileEvent> update(UpdateProfileCommand command) {
    return CompletableFuture.supplyAsync(() -> {
      // 1. update data by jpa(RDB)
      com.example.research.profile.entity.storage.Profile existProfile = profileStorageRepository
          .findById(command.getId()).orElseThrow(IllegalStateException::new);
      existProfile.setName(
          StringUtils.isEmpty(command.getName()) ?
              existProfile.getName() : command.getName()
      );
      existProfile.setAge(command.getAge() == null ? existProfile.getAge() : command.getAge());
      existProfile.setSex(command.getSex() == null ? existProfile.getSex() : command.getSex());
      existProfile.setUpdatedAt(LocalDateTime.now());
      profileStorageRepository.save(existProfile);

      // 2. insert event-data to event-store(RDB)
      Long latestVersion = profileEventStoreRepository.findTopByIdentifierOrderByNoDesc(command.getId())
          .map(com.example.research.profile.entity.storage.ProfileEvent::getVersion)
          .orElse(0L);
      String payload = generatePayload(command);
      com.example.research.profile.entity.storage.ProfileEvent profileRawEvent
          = com.example.research.profile.entity.storage.ProfileEvent.from(command, latestVersion + 1, payload);

      // 3. update data by cache store(redis)
      profileRepository.save(Profile.from(command)).subscribe();
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
