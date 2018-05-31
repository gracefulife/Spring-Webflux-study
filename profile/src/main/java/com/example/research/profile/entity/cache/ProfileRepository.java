package com.example.research.profile.entity.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProfileRepository {
  @Autowired ReactiveRedisOperations<String, Profile> template;

  public ProfileRepository(ReactiveRedisOperations<String, Profile> template) {
    this.template = template;
  }

  public Flux<Profile> findAll() {
    return template.<String, Profile>opsForHash().values("profiles");
  }

  public Mono<Profile> findById(String id) {
    return template.<String, Profile>opsForHash().get("profiles", id).single();
  }

  public Mono<Profile> save(Profile profile) {
    return template.<String, Profile>opsForHash().put("profiles", profile.getId(), profile)
        .map(p -> profile);

  }

  public Mono<Void> deleteById(String id) {
    return template.<String, Profile>opsForHash().remove("profiles", id)
        .flatMap(p -> Mono.empty());
  }

  public Mono<Boolean> deleteAll() {
    return template.<String, Profile>opsForHash().delete("profiles");
  }
}

