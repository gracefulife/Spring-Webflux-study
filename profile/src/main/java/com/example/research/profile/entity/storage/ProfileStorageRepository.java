package com.example.research.profile.entity.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileStorageRepository extends JpaRepository<Profile, String> {
}
