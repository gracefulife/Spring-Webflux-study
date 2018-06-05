package com.example.research.targeting;

import com.namics.commons.random.RandomData;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * TODO embedded mongo
 * Integration test for storage entity
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationContext.class)
@ActiveProfiles("test")
public class TargetingTests {
  @Autowired private TargetingRepository targetingRepository;

  @Test public void 타게팅_생성() {
    // GIVEN
    Targeting targeting = generateData();

    // WHEN
    Targeting savedTarget = targetingRepository.save(targeting).block();

    // THEN
    assertNotNull(savedTarget);
    assertEquals(savedTarget, targetingRepository.findById(savedTarget.getId()).block());
  }

  private static Targeting generateData() {
    Set<Profile> profileSet = new LinkedHashSet<>();
    profileSet.add(new Profile(UUID.randomUUID().toString(), UUID.randomUUID().toString()));
    profileSet.add(new Profile(UUID.randomUUID().toString(), UUID.randomUUID().toString()));

    return new Targeting(null, RandomData.name(), UUID.randomUUID().toString(),
        profileSet, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
  }
}
