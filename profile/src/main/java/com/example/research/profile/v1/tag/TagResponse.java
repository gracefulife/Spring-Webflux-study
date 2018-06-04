package com.example.research.profile.v1.tag;

import com.example.research.profile.entity.cache.Profile;
import com.example.research.profile.entity.storage.Tag;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagResponse {
  private Long no;
  private Set<String> profileIds;
  private Set<Profile> profiles;
  private LocalDateTime createdAt;

  public static TagResponse from(Tag tag) {
    return new TagResponse(tag.getNo(), tag.getProfiles(), Collections.emptySet(), tag.getCreatedAt());
  }

  public static TagResponse from(Tag tag, Set<Profile> profiles) {
    return new TagResponse(tag.getNo(), Collections.emptySet(), profiles, tag.getCreatedAt());
  }
}
