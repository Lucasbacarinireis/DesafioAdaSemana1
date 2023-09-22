package com.cielo.desafio01.repository;

import com.cielo.desafio01.model.CustomerFeedback;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface FeedbackRepository extends MongoRepository<CustomerFeedback, String> {
}
