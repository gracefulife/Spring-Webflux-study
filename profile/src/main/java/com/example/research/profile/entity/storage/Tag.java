package com.example.research.profile.entity.storage;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "tag")
public class Tag {
  @Id
  @Column(name = "no")
  Long no;

  @Column(name = "name") String name;

  @CreatedDate
  @Column(name = "created_at") LocalDateTime createdAt;
  @CreatedDate
  @LastModifiedDate
  @Column(name = "updated_at") LocalDateTime updatedAt;
}
