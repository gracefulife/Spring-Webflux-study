package com.example.research.profile.entity.command;

import com.example.research.profile.entity.storage.Profile;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class ProfileSavedCommand extends ProfileCommand {
  public static final String TAG = ProfileSavedCommand.class.getSimpleName();

  @NonNull String id;
  @NonNull String name;
  @NonNull Integer age;
  @NonNull String sex; // man, woman
  @NonNull LocalDateTime createdAt;

  public static ProfileSavedCommand from(Profile storeProfile) {
    return new ProfileSavedCommand(
        storeProfile.getId(),
        storeProfile.getName(), storeProfile.getAge(),
        storeProfile.getSex(), storeProfile.getCreatedAt()
    );
  }

  @Override public String getTag() {
    return TAG;
  }
}
