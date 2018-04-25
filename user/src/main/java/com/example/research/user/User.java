package com.example.research.user;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User implements Serializable {
  @Id
  private Long no;
  private String name;
  private String cellphone;
}
