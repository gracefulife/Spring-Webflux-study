package com.example.research.loader.line;

import com.example.research.loader.domain.MessageRequest;
import com.example.research.loader.domain.MessageService;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import org.springframework.integration.support.json.Jackson2JsonObjectMapper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@LineMessageHandler
public class LineMessageReceiver {
  private final Jackson2JsonObjectMapper jackson2JsonObjectMapper;
  private final MessageService messageService;

  // Response
  @EventMapping
  public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
    try {
      // TODO reactive support
      messageService.save(
          new MessageRequest(
              event.getMessage().getText(), event.getSource().getUserId(),
              event.getMessage().getId(), LocalDateTime.ofInstant(event.getTimestamp(), ZoneOffset.UTC),
              jackson2JsonObjectMapper.toJson(event)
          )
      )
          .block();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new TextMessage(event.getMessage().getText());
  }

  @EventMapping
  public void handleDefaultMessageEvent(Event event) {
    log.info("[LineMessageReceiver] event : {}", event);
  }
}
