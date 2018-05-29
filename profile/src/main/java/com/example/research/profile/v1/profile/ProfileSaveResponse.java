package com.example.research.profile.v1.profile;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ProfileSaveResponse {
  @NonNull String id;
}
