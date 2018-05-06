package com.example.research.survey;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Survey implements Serializable {
  @Id
  private String no;
  private String name;
  private Integer target;
//  private List<Question> questions; // not yet implements
}
