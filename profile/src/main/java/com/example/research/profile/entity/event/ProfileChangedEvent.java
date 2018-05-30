package com.example.research.profile.entity.event;

import com.example.research.profile.entity.storage.Profile;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileChangedEvent extends ProfileEvent {
  public static final String TAG = ProfileChangedEvent.class.getSimpleName();

  String id;
  String name;
  Integer age;
  String sex; // man, woman
  LocalDateTime createdAt;

  public static ProfileChangedEvent from(Profile storeProfile) {
    return new ProfileChangedEvent(
        storeProfile.getId(),
        storeProfile.getName(), storeProfile.getAge(),
        storeProfile.getSex(), storeProfile.getCreatedAt()
    );
  }

  @Override public String getTag() {
    return TAG;
  }
}
