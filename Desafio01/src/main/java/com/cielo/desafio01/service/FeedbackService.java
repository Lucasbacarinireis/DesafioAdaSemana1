package com.cielo.desafio01.service;

import com.cielo.desafio01.model.CustomerFeedback;
import com.cielo.desafio01.enums.FeedbackStatus;
import com.cielo.desafio01.enums.FeedbackType;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import org.springframework.data.mongodb.core.query.Query;

@Service
public class FeedbackService {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public FeedbackService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void adicionarFeedback(CustomerFeedback feedback) {
        mongoTemplate.save(feedback);
    }

    
    public void atualizarStatusFeedback(String feedbackId, FeedbackStatus novoStatus) {
        CustomerFeedback existingFeedback = mongoTemplate.findById(feedbackId, CustomerFeedback.class);
        if (existingFeedback != null) {
            existingFeedback.setStatus(novoStatus);
            mongoTemplate.save(existingFeedback);
        } else {
            throw new FeedbackNotFoundException("Feedback não encontrado com ID: " + feedbackId);
        }
    }

    public List<CustomerFeedback> listarTodosFeedbacks() {
        return mongoTemplate.findAll(CustomerFeedback.class);
    }

    public List<CustomerFeedback> listarFeedbacksPorTipo(FeedbackType tipo) {
        Query query = new Query(Criteria.where("type").is(tipo));
        return mongoTemplate.find(query, CustomerFeedback.class);
    }
}
