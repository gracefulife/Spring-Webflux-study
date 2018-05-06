package com.example.research.survey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SurveyResponse {
  private String no;
  private String name;
  private Integer targets;
//  private List<Question> questions // not yet implements
}
