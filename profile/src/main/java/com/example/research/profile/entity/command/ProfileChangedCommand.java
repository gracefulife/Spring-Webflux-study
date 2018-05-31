package com.example.research.profile.entity.command;

import com.example.research.profile.entity.storage.Profile;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileChangedCommand extends ProfileCommand {
  public static final String TAG = ProfileChangedCommand.class.getSimpleName();

  String id;
  String name;
  Integer age;
  String sex; // man, woman
  LocalDateTime createdAt;

  public static ProfileChangedCommand from(Profile storeProfile) {
    return new ProfileChangedCommand(
        storeProfile.getId(),
        storeProfile.getName(), storeProfile.getAge(),
        storeProfile.getSex(), storeProfile.getCreatedAt()
    );
  }

  @Override public String getTag() {
    return TAG;
  }
}
