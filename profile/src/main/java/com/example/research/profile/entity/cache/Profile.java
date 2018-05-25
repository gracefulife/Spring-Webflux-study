package com.example.research.profile.entity.cache;

import org.springframework.data.annotation.Id;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
  @Id String id; // user system ID
  Set<Tag> tags;
}
