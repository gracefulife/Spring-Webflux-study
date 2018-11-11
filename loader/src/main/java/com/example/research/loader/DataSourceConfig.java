package com.example.research.loader;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import lombok.AllArgsConstructor;

@Configuration
@EnableReactiveMongoRepositories
@AllArgsConstructor
public class DataSourceConfig extends AbstractReactiveMongoConfiguration {
  private MongoProperties mongoProperties;

  @Override public MongoClient reactiveMongoClient() {
    return MongoClients.create();
  }

  @Override protected String getDatabaseName() {
    return mongoProperties.getDatabase();
  }
}
