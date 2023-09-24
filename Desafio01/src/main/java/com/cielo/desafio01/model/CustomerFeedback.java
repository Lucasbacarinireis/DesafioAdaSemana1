package com.cielo.desafio01.model;

import com.cielo.desafio01.enums.FeedbackStatus;
import com.cielo.desafio01.enums.FeedbackType;
import java.util.UUID;

public class CustomerFeedback {
    private String id;
    private FeedbackType type;
    private String message;
    private FeedbackStatus status;

    public CustomerFeedback() {
        this.id = UUID.randomUUID().toString();
        this.type = FeedbackType.SUGESTAO;
        this.message = "";
        this.status = FeedbackStatus.RECEBIDO;
    }

    public CustomerFeedback(String id, FeedbackType type, String message, FeedbackStatus status) {
        this.id = id;
        this.type = type;
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

    public FeedbackType getType() {
        return type;
    }

    public void setType(FeedbackType type) {
        this.type = type;
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

    @Override
    public String toString() {
        return "CustomerFeedback{" +
            "id='" + id + '\'' +
            ", type=" + type +
            ", message='" + message + '\'' +
            ", status=" + status +
            '}';
    }
}
