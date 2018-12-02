package com.example.research.messenger;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LikertScale implements Serializable {
  private String stronglyDisagree;
  private String disagree;
  private String neitherDisagreeOrAgree;
  private String agree;
  private String stronglyAgree;
}
