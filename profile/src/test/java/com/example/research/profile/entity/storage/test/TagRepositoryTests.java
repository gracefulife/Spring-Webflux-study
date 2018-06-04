package com.example.research.profile.entity.storage.test;

import com.example.research.profile.TestApplicationContext;
import com.example.research.profile.entity.storage.Tag;
import com.example.research.profile.entity.storage.TagStorageRepository;
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
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
public class TagRepositoryTests {
  @Autowired private TagStorageRepository tagStorageRepository;
  @Autowired @Qualifier("profile_entity_manager") EntityManager em;

  @Test public void 태그_생성() {
    final Tag generatedTag = generateData();
    Tag savedTag = tagStorageRepository.save(generatedTag);
    Optional<Tag> foundTag = tagStorageRepository.findById(savedTag.getNo());

    assertNotNull(savedTag);

    foundTag.map(tag -> {
      assertEquals(savedTag.getName(), tag.getName());
      assertEquals(savedTag.getNo(), tag.getNo());
      return tag;
    }).orElseThrow(() -> new IllegalStateException("tag must not be null"));
  }

  @Test public void 태그_프로필_맵_조회() {
    // GIVEN
    final Tag generatedTag = generateData();
    Set<String> mockProfileSets = new LinkedHashSet<>();
    mockProfileSets.add(RandomData.alphabetic(16));
    mockProfileSets.add(RandomData.alphabetic(16));
    mockProfileSets.add(RandomData.alphabetic(16));
    generatedTag.setProfiles(mockProfileSets);

    // WHEN
    Tag savedTag = tagStorageRepository.save(generatedTag);
    Optional<Tag> foundTag = tagStorageRepository.findById(savedTag.getNo());

    // THEN
    assertNotNull(savedTag);
    foundTag.map(tag -> {
      assertEquals(savedTag.getName(), tag.getName());
      assertEquals(savedTag.getNo(), tag.getNo());
      assertFalse(savedTag.getProfiles().isEmpty());
      return tag;
    }).orElseThrow(() -> new IllegalStateException("tag must not be null"));

  }

  private static Tag generateData() {
    return new Tag(RandomData.name(), LocalDateTime.now(), LocalDateTime.now());
  }
}
