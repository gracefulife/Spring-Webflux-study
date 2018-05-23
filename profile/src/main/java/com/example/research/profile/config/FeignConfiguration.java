package com.example.research.profile.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;

@Configuration
public class FeignConfiguration {
  @Bean Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }
}
