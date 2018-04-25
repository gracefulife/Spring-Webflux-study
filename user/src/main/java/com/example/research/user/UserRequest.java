package com.example.research.user;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class UserRequest implements Serializable {
  private Long no;
  private String name;
  private String cellphone;

  public static UserRequest from(User user) {
    return new UserRequest(
        user.getNo(), user.getName(), user.getCellphone()
    );
  }
}
