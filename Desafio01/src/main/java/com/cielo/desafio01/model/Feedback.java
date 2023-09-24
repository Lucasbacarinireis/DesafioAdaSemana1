package com.cielo.desafio01.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.cielo.desafio01.enums.FeedbackStatus;

@Document(collection = "feedbacks")
public class Feedback {
    @Id
    private String id;
    private String message;
    private FeedbackStatus status;

    // Construtor padrão (vazio)
    public Feedback() {
    }

    // Construtor com todos os campos (se necessário)
    public Feedback(String message, FeedbackStatus status) {
        this.message = message;
        this.status = status;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FeedbackStatus getStatus() {
        return status;
    }

    public void setStatus(FeedbackStatus status) {
        this.status = status;
    }
}