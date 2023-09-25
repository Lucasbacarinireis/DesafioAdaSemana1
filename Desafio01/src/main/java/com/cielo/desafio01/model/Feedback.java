package com.cielo.desafio01.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.cielo.desafio01.enums.FeedbackStatus;
import com.cielo.desafio01.enums.FeedbackType;

@Document(collection = "feedbacks")
public class Feedback {
    @Id
    private String id;
    private String message;
    private FeedbackStatus status;
    private FeedbackType type;

    public Feedback() {
    }

    public Feedback(String message, FeedbackStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setType(FeedbackType type) {
        this.type = type;
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
