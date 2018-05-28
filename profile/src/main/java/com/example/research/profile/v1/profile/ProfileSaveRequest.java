package com.example.research.profile.v1.profile;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class ProfileSaveRequest implements Serializable {
  @NonNull String name;
  @NonNull Integer age;
  @NonNull String sex; // man, woman
  @NonNull LocalDateTime createdAt;
}
