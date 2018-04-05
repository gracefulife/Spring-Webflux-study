package com.opensurvey.sample;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Survey {
  Long id;
  String title;
}
