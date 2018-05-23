package com.example.research.profile.config;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "profileEntityManagerFactory",
    transactionManagerRef = "profileTransactionManager",
    basePackages = "com.example.research.profile.entity"
)
public class ProfileDatabaseConfig {

  @Bean(name = "profile_datasource")
  @ConfigurationProperties(prefix = "profile.datasource")
  public HikariDataSource profileDataSource() {
    return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }

  @Bean(name = "profile_entity_manager")
  public LocalContainerEntityManagerFactoryBean profileEntityManagerFactory(
      @Qualifier("profile_datasource") DataSource dataSource, EntityManagerFactoryBuilder builder) {
    return builder.dataSource(dataSource)
        .packages("com.example.research.profile.entity")
        .build();
  }

  @Bean
  public PlatformTransactionManager profileTransactionManager(
      @Qualifier("profile_entity_manager") EntityManagerFactory profileEntityManagerFactory) {
    return new JpaTransactionManager(profileEntityManagerFactory);
  }
}