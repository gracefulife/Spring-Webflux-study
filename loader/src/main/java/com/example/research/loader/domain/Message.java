package com.example.research.loader.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {
  public String id;
  @NotBlank @Size(max = 200) public String text;

  public String sendUserId;
  public String providedMessageId;
  public LocalDateTime timestamp;
  public String extra;
}
