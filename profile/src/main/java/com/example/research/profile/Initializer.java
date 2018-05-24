package com.example.research.profile;

import com.example.research.profile.entity.cache.Profile;
import com.example.research.profile.entity.cache.ProfileRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Component
@Slf4j
@AllArgsConstructor
public class Initializer implements CommandLineRunner {
  private final ProfileRepository profileRepository;

  @Override public void run(String... args) {
    log.info("init ...");
    this.profileRepository.findAll()
        .thenMany(Flux.just("one", "two")
            .flatMap(titles -> {
              String id = UUID.randomUUID().toString();
              return this.profileRepository.save(new Profile(id, Collections.emptySet()));
            }))
        .log()
        .subscribe(
            profile -> log.info("profile .. {}" + profile),
            null,
            () -> log.info("done")
        );
  }
}
