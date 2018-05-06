package com.example.research.survey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SurveyRequest {
  private String no;
  private String name;
  private String userNo;
}
