package com.example.research.profile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ProfileApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProfileApplication.class, args);
  }
}
