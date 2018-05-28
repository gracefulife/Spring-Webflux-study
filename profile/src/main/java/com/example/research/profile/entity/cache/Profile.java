package com.example.research.profile.entity.cache;

import org.springframework.data.annotation.Id;

import java.util.Collections;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
  @Id String id; // user system ID
  Set<Long> tagIds;

  public static Profile from(com.example.research.profile.entity.storage.Profile profile) {
    Profile cache = new Profile();
    cache.id = profile.getId();
    cache.tagIds = Collections.emptySet(); // FIXME .. profiles 정보에 tag 아이디가 필요할지 아직 잘 모르겠다.
    return cache;
  }
}
