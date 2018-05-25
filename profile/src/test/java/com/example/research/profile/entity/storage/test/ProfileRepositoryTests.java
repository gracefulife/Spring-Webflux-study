package com.example.research.profile.entity.storage.test;

import com.example.research.profile.TestApplicationContext;
import com.example.research.profile.entity.storage.Profile;
import com.example.research.profile.entity.storage.ProfileStorageRepository;
import com.namics.commons.random.RandomData;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.EntityManager;

import static com.namics.commons.random.RandomData.name;
import static com.namics.commons.random.RandomData.randomBoolean;
import static com.namics.commons.random.RandomData.randomInteger;
import static com.namics.commons.random.RandomData.randomString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Integration test for storage entity
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationContext.class)
@Transactional(
    transactionManager = "profile_transaction_manager",
    propagation = Propagation.REQUIRES_NEW,
    rollbackFor = Exception.class
)
@ActiveProfiles("test")
public class ProfileRepositoryTests {
  @Autowired private ProfileStorageRepository profileStorageRepository;
  @Autowired @Qualifier("profile_entity_manager") EntityManager em;

  @Test public void 프로필_생성() {
    final Profile generatedProfile = generateData();
    Profile savedProfile = profileStorageRepository.save(generatedProfile);
    Optional<Profile> foundProfile = profileStorageRepository.findById(savedProfile.getId());

    assertNotNull(savedProfile);

    foundProfile.map(profile -> {
      assertEquals(savedProfile.getId(), profile.getId());
      assertEquals(savedProfile.getName(), profile.getName());
      assertEquals(savedProfile.getAge(), profile.getAge());
      assertEquals(savedProfile.getActive(), profile.getActive());
      return profile;
    }).orElseThrow(() -> new IllegalStateException("profile must not be null"));
  }

  private static Profile generateData() {
    return new Profile(randomString(), name(), randomInteger(10, 40),
        randomBoolean() ? "man" : "woman", randomBoolean(),
        LocalDateTime.now(), LocalDateTime.now());
  }
}
