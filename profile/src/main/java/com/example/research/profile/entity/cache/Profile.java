package com.example.research.profile.entity.cache;

import com.example.research.profile.entity.storage.ProfileEvent;

import org.springframework.data.annotation.Id;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
  @Id String id; // user system ID
  String name;
  Integer age;
  String sex; // man, woman
  Boolean active; // 활성 유저 ? 프로필에서 판단해주어야 함.

  // TODO snapshot 버전 순서를 보장해줄 수 있는 자료구조를 고민해야 함
  Set<ProfileEvent> events = new LinkedHashSet<>();

  public static Profile from(com.example.research.profile.entity.storage.Profile profile) {
    return from(profile, Collections.emptySet());
  }

  public static Profile from(com.example.research.profile.entity.storage.Profile profile,
                             Set<ProfileEvent> events) {
    Profile cache = new Profile();
    cache.id = profile.getId();
    cache.name = profile.getName();
    cache.age = profile.getAge();
    cache.sex = profile.getSex(); // man, woman
    cache.active = profile.getActive(); // 활성 유저 ? 프로필에서 판단해주어야 함.

    cache.events = events;
    return cache;
  }
}
