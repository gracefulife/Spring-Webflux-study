package com.example.research.profile.entity.storage;

import com.example.research.profile.core.Event;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Entity
@Table(name = "profile_event")
public class ProfileEvent implements Event<String> {
  @Id
  @Column(name = "no", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long no;

  @Column(name = "identifier", nullable = false)
  private String identifier;

  @Column(name = "type", nullable = false)
  private String type;

  @Column(name = "version", nullable = false)
  private Long version;

  @Column(name = "payload", nullable = false)
  private String payload;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;
}
