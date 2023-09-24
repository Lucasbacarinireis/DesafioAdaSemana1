package com.cielo.desafio01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.cielo.desafio01.enums.FeedbackStatus;
import com.cielo.desafio01.model.Feedback;
import com.cielo.desafio01.repository.FeedbackRepository;

@Component
public class FeedbackTestInitializer implements CommandLineRunner {

    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackTestInitializer(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Feedback feedback = new Feedback();
        feedback.setMessage("Este é um feedback de teste.");
        feedback.setStatus(FeedbackStatus.RECEBIDO);

        feedbackRepository.save(feedback);
}

}