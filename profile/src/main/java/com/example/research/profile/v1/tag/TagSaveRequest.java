package com.example.research.profile.v1.tag;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagSaveRequest implements Serializable {
  private Long no; // optional
  private String name;
}
