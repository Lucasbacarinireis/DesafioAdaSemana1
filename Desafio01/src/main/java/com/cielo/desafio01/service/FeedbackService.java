package com.cielo.desafio01.service;

import com.cielo.desafio01.enums.FeedbackStatus;
import com.cielo.desafio01.model.CustomerFeedback;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.cielo.desafio01.enums.FeedbackType;

@Service
public class FeedbackService {

    private final List<CustomerFeedback> feedbacks = new ArrayList<>();

    public void adicionarFeedback(CustomerFeedback feedback) {
        feedbacks.add(feedback);
    }

    public void atualizarStatusFeedback(String feedbackId, FeedbackStatus novoStatus) {
        for (CustomerFeedback feedback : feedbacks) {
            if (feedback.getId().equals(feedbackId)) {
                feedback.setStatus(novoStatus);
                break;
            }
        }
    }

    public List<CustomerFeedback> listarTodosFeedbacks() {
        return feedbacks;
    }

    public List<CustomerFeedback> listarFeedbacksPorTipo(FeedbackType tipo) {
        List<CustomerFeedback> feedbacksPorTipo = new ArrayList<>();
        for (CustomerFeedback feedback : feedbacks) {
            if (feedback.getType() == tipo) {
                feedbacksPorTipo.add(feedback);
            }
        }
        return feedbacksPorTipo;
    }
}
