package com.example.research.profile.entity.cache.test;

import com.example.research.profile.TestApplicationContext;
import com.example.research.profile.entity.storage.ProfileStorageRepository;
import com.example.research.profile.entity.storage.TagStorageRepository;

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

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationContext.class)
@Transactional(
    transactionManager = "profile_transaction_manager",
    propagation = Propagation.REQUIRES_NEW,
    rollbackFor = Exception.class
)
@ActiveProfiles("test")
public class TagRepositoryTests {
  // 무엇을 테스트해야하나 ?
  /**
   * 태그와, 프로필이 섞여있는 상황에서, 다수의 프로필을 포함하는 tag 를 빠르게 가져올 수 있는가. tag 의 캐시 기능을 테스트하면 됨.
   * 타 서비스에서 어떤 자료구조로 가져가야할지 잘 설계해보아야 함
   */

  @Autowired private TagStorageRepository tagStorageRepository;
  @Autowired @Qualifier("profile_entity_manager") EntityManager em;

  @Autowired ProfileStorageRepository panelStorageRepository;
  @Autowired LettuceConnectionFactory connectionFactory;

  @BeforeTransaction
  public void setup() {
    connectionFactory.getConnection().flushDb();
  }

  @Test
  public void test_anything() {

  }
}
