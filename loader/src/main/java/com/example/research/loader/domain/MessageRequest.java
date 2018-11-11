package com.example.research.loader.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest implements Serializable {
  private String message;
  private String sendUserId;
  private String providedMessageId;
  private LocalDateTime timestamp;

  private String extra;

  public Message to() {
    return new Message(null, message, sendUserId, providedMessageId, timestamp, extra);
  }
}
