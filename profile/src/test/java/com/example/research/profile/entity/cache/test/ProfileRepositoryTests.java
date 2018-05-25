package com.example.research.profile.entity.cache.test;

import com.example.research.profile.TestApplicationContext;
import com.example.research.profile.entity.cache.Profile;
import com.example.research.profile.entity.cache.ProfileRepository;
import com.example.research.profile.entity.storage.Tag;
import com.example.research.profile.entity.storage.TagStorageRepository;
import com.namics.commons.random.RandomData;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import reactor.test.StepVerifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationContext.class)
@Transactional(
    transactionManager = "profile_transaction_manager",
    propagation = Propagation.REQUIRES_NEW,
    rollbackFor = Exception.class
)
@ActiveProfiles("test")
public class ProfileRepositoryTests {
  @Autowired private TagStorageRepository tagStorageRepository;
  @Autowired @Qualifier("profile_entity_manager") EntityManager em;

  @Autowired ProfileRepository profileRepository;
  @Autowired LettuceConnectionFactory connectionFactory;

  @BeforeTransaction
  public void setup() {
    connectionFactory.getConnection().flushDb();
  }

  @Test public void 프로필_조회() {
    final List<Tag> generatedTags = generateRandomTagData();

    generatedTags.forEach(generatedTag -> {
      Tag savedTag = tagStorageRepository.save(generatedTag);
      Optional<Tag> foundTag = tagStorageRepository.findById(savedTag.getNo());

      assertNotNull(savedTag);

      foundTag.map(tag -> {
        assertEquals(savedTag.getName(), tag.getName());
        assertEquals(savedTag.getNo(), tag.getNo());
        return tag;
      }).orElseThrow(() -> new IllegalStateException("tag must not be null"));
    });

    // to redis
    StepVerifier.create(profileRepository.save(generateProfile(generatedTags)))
        .expectNextCount(1)
        .verifyComplete();

    StepVerifier.create(profileRepository.findAll())
        .expectNextCount(1)
        .verifyComplete();
  }

  private static Profile generateProfile(List<Tag> tags) {
    return new Profile(
        RandomData.alphabetic(20),
        tags.stream().map(Tag::getNo).collect(Collectors.toSet())
    );
  }

  private static List<Tag> generateRandomTagData() {
    List<Tag> tags = new ArrayList<>();
    for (int i = 0; i < RandomData.randomInteger(1, 30); i++) {
      tags.add(new Tag(RandomData.name(), LocalDateTime.now(), LocalDateTime.now()));
    }
    return tags;
  }
}
