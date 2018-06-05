package com.example.research.user;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse implements Serializable {
  private String id;
  private String name;
  private String cellphone;

  public static UserResponse from(User user) {
    return new UserResponse(
        user.getId(), user.getName(), user.getCellphone()
    );
  }
}
