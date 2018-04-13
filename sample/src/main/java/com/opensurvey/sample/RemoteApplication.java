package com.opensurvey.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class RemoteApplication {
  @RestController
  public static class RemoteController {
    @GetMapping("/remote") public String remote() throws InterruptedException {
      // step3 만약 이 쓰레드가 블락된다면, 이 서버의 쓰레드수는 계속 늘 것..
      Thread.sleep(2000);
      return "remote";
    }
  }

  public static void main(String[] args) {
    System.setProperty("SERVER_PORT", "8081");
    System.setProperty("server.tomcat.max-threads", "1000");
    SpringApplication.run(RemoteApplication.class, args);
  }
}
