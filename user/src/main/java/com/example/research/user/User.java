package com.example.research.user;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import reactor.core.publisher.Mono;

@Data
@AllArgsConstructor
public class User implements Serializable {
  @Id
  private String id;
  private String name;
  private String cellphone;

  private String sex;
  private Integer age;

  public static User from(UserRequest request) {
    return new User(null, request.getName(), request.getCellphone(), request.getSex(), request.getAge());
  }
}
