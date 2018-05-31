package com.example.research.profile.entity.command;

import com.example.research.profile.v1.profile.ProfileSaveRequest;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class CreateProfileCommand extends ProfileCommand {
  public static final String TAG = CreateProfileCommand.class.getSimpleName();

  @NonNull String id;
  @NonNull String name;
  @NonNull Integer age;
  @NonNull String sex; // man, woman
  @NonNull LocalDateTime createdAt;

  public static CreateProfileCommand from(ProfileSaveRequest storeProfile) {
    return new CreateProfileCommand(
        storeProfile.getId(),
        storeProfile.getName(), storeProfile.getAge(),
        storeProfile.getSex(), LocalDateTime.now()
    );
  }

  @Override public String getTag() {
    return TAG;
  }
}
