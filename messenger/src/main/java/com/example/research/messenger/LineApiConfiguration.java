package com.example.research.messenger;

import com.linecorp.bot.client.LineMessagingClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LineApiConfiguration {
  @Bean
  public LineMessagingClient lineMessagingClient(
      @Value("${line.channel.token}") String channelToken) {
    return LineMessagingClient.builder(channelToken).build();
  }
}
