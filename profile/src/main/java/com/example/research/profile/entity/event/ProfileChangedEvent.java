package com.example.research.profile.entity.event;

import com.example.research.profile.entity.storage.Profile;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProfileChangedEvent implements ProfileEvent {
  @Getter public static final String ID = ProfileChangedEvent.class.getSimpleName();

  @NonNull String id;
  @NonNull String name;
  @NonNull Integer age;
  @NonNull String sex; // man, woman
  @NonNull LocalDateTime createdAt;

  public static ProfileChangedEvent from(Profile storeProfile) {
    return new ProfileChangedEvent(
        storeProfile.getId(),
        storeProfile.getName(), storeProfile.getAge(),
        storeProfile.getSex(), storeProfile.getCreatedAt()
    );
  }

  @Override public String getTag() {
    return ID;
  }
}
