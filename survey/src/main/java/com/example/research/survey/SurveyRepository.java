package com.example.research.survey;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SurveyRepository extends ReactiveCrudRepository<Survey, String> {
}
