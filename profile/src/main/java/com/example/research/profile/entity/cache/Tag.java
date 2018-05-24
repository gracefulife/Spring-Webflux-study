package com.example.research.profile.entity.cache;

import org.springframework.data.annotation.Id;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tag {
  @Id Long id;
  Set<Profile> profiles;
}
