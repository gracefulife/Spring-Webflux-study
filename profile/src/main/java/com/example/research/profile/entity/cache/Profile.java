package com.example.research.profile.entity.cache;

import org.springframework.data.annotation.Id;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
  @Id String id; // user system ID
  Set<Long> tagIds;
}
