package com.example.research.profile.config;

import com.example.research.profile.entity.cache.Profile;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class RedisConfig {

  @org.springframework.context.annotation.Profile("test")
  @Bean
  public LettuceConnectionFactory connectionFactoryForTest() {
    log.info("call redisConnectionFactory");
    return new LettuceConnectionFactory("192.168.99.100", 32768);
  }

  @org.springframework.context.annotation.Profile("dev")
  @Bean
  public LettuceConnectionFactory connectionFactoryForDev() {
    log.info("call redisConnectionFactory");
    return new LettuceConnectionFactory("192.168.99.100", 32768);
  }

  @Bean
  public ReactiveRedisTemplate<String, Profile> reactiveJsonProfileRedisTemplate(
      LettuceConnectionFactory connectionFactory) {
    log.info("call reactiveJsonPersonRedisTemplate");
    RedisSerializationContext<String, Profile> serializationContext = RedisSerializationContext
        .<String, Profile>newSerializationContext(new StringRedisSerializer())
        .hashKey(new StringRedisSerializer())
        .hashValue(new Jackson2JsonRedisSerializer<>(Profile.class))
        .build();
    return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
  }

//  @Bean
//  public ReactiveRedisTemplate<String, Object> reactiveJsonObjectRedisTemplate(
//      ReactiveRedisConnectionFactory connectionFactory) {
//
//    RedisSerializationContext.RedisSerializationContextBuilder<String, Object> builder = RedisSerializationContext
//        .newSerializationContext(new StringRedisSerializer());
//
//    RedisSerializationContext<String, Object> serializationContext = builder.value(
//        new GenericJackson2JsonRedisSerializer("_type")).build();
//
//    return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
//  }

}
