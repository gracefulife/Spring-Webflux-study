package com.example.research.profile.entity.storage;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "profile")
public class Profile {

  // user 서비스에서 UUID 를 주입받아 사용할 것임.
  @NonNull
  @Id
  @Column(name = "id")
  String id;

  @NonNull
  @Column(name = "name") String name;
  @NonNull
  @Column(name = "age") Integer age;
  @NonNull
  @Column(name = "sex") String sex; // man, woman
  @NonNull
  @Column(name = "active") Boolean active; // 활성 유저 ? 프로필에서 판단해주어야 함.

  @NonNull
  @CreatedDate
  @Column(name = "created_at") LocalDateTime createdAt;

  @NonNull
  @CreatedDate
  @LastModifiedDate
  @Column(name = "updated_at") LocalDateTime updatedAt;
}
