package com.example.research.profile.entity.command;

import com.example.research.profile.entity.storage.Profile;
import com.example.research.profile.v1.profile.ProfileSaveRequest;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileCommand extends ProfileCommand {
  public static final String TAG = UpdateProfileCommand.class.getSimpleName();

  String id;
  String name;
  Integer age;
  String sex; // man, woman
  LocalDateTime createdAt;

  public static UpdateProfileCommand from(Profile storeProfile) {
    return new UpdateProfileCommand(
        storeProfile.getId(),
        storeProfile.getName(), storeProfile.getAge(),
        storeProfile.getSex(), storeProfile.getCreatedAt()
    );
  }

  public static UpdateProfileCommand from(String id, ProfileSaveRequest request) {
    return new UpdateProfileCommand(
        id, request.getName(), request.getAge(),
        request.getSex(), LocalDateTime.now()
    );

  }

  @Override public String getTag() {
    return TAG;
  }
}
