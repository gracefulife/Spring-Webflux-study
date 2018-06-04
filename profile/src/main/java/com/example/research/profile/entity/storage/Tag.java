package com.example.research.profile.entity.storage;

import com.example.research.profile.v1.tag.TagSaveRequest;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "tag")
public class Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "no")
  Long no;

  @NonNull
  @Column(name = "name") String name;

  @NonNull
  @CreatedDate
  @Column(name = "created_at") LocalDateTime createdAt;

  @NonNull
  @CreatedDate
  @LastModifiedDate
  @Column(name = "updated_at") LocalDateTime updatedAt;

  public static Tag from(TagSaveRequest tagSaveRequest) {
    Tag tag = new Tag();
    tag.name = tagSaveRequest.getName();
    tag.createdAt = LocalDateTime.now();
    tag.updatedAt = LocalDateTime.now();
    return tag;
  }
}
