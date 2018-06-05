package com.example.research.targeting;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Targeting implements Serializable {
  @Id
  private String id;
  private String name;
  private String surveyId;
  private Set<Profile> profileIds;

  private LocalDateTime deployAt;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
