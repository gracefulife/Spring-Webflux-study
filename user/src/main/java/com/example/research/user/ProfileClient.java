package com.example.research.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO retry, failover
@FeignClient(name = "PROFILE-API",
    configuration = FeignConfiguration.class)
public interface ProfileClient {
  @RequestMapping(value = "/api/v1/profiles", method = RequestMethod.POST)
  void save(@RequestBody ProfileSaveRequest request);

  @Data
  @AllArgsConstructor
  @NoArgsConstructor public class ProfileSaveRequest {
    private String id;
    private String name;
    private Integer age;
    private String sex; // man, woman

    public static ProfileSaveRequest from(User user) {
      return new ProfileSaveRequest(user.getNo(), user.getName(), user.getAge(), user.getSex());
    }
  }
}
