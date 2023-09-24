package com.cielo.desafio01.repository;

import com.cielo.desafio01.model.Feedback;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeedbackRepository extends MongoRepository<Feedback, String> {
}
