package com.example.research.profile.entity.cache;

import com.example.research.profile.entity.command.UpdateProfileCommand;
import com.example.research.profile.entity.command.CreateProfileCommand;
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
  @Id String id; // user system TAG
  String name;
  Integer age;
  String sex; // man, woman
  Boolean active; // 활성 유저 ? 프로필에서 판단해주어야 함.

  // TODO snapshot 버전 순서를 보장해줄 수 있는 자료구조를 고민해야 함
  Set<ProfileEvent> events = new LinkedHashSet<>();

  public static Profile from(com.example.research.profile.entity.storage.Profile profile) {
    return from(profile, Collections.emptySet());
  }

  // 생성 이벤트에서 왔으므로, 항상 active
  public static Profile from(CreateProfileCommand event) {
    return from(event.getId(), event.getName(), event.getAge(),
        event.getSex(), true, Collections.emptySet());
  }

  // TODO active 상태
  public static Profile from(UpdateProfileCommand event) {
    return from(event.getId(), event.getName(), event.getAge(),
        event.getSex(), true, Collections.emptySet());
  }

  public static Profile from(com.example.research.profile.entity.storage.Profile profile,
                             Set<ProfileEvent> events) {
    return from(profile.getId(), profile.getName(), profile.getAge(),
        profile.getSex(), profile.getActive(), events);
  }

  public static Profile from(String id, String name, Integer age,
                             String sex, Boolean active, Set<ProfileEvent> events) {
    Profile cache = new Profile();
    cache.id = id;
    cache.name = name;
    cache.age = age;
    cache.sex = sex;
    cache.active = active;
    cache.events = events;
    return cache;
  }
}
