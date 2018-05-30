package com.example.research.profile.entity.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileEventStoreRepository extends JpaRepository<ProfileEvent, Long> {
  Optional<ProfileEvent> findTopByIdentifierOrderByNoDesc(String identifier);
}
