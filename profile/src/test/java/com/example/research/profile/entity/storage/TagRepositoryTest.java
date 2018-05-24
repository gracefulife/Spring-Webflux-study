package com.example.research.profile.entity.storage;

import com.example.research.profile.TestApplicationContext;
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

import javax.persistence.EntityManager;

/**
 * Integration test for storage entity
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationContext.class)
@Transactional(
    transactionManager = "profileTransactionManager",
    propagation = Propagation.REQUIRES_NEW,
    rollbackFor = Exception.class
)
@ActiveProfiles("test")
public class TagRepositoryTest {
  @Autowired private TagStorageRepository tagStorageRepository;
  @Autowired @Qualifier("profile_entity_manager") EntityManager em;

  @Test public void 태그_생성() {
    final Tag tag = generateData();
    tagStorageRepository.save(tag);
  }

  @Test public void 태그_조회() {
  }

  private static Tag generateData() {
    return new Tag(RandomData.randomLong(), RandomData.name(), LocalDateTime.now(), LocalDateTime.now());
  }
}
