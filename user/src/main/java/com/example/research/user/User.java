package com.example.research.user;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User implements Serializable {
  private Long no;
  private String name;
  private String cellphone;
}
