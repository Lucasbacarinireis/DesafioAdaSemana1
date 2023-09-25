package com.cielo.desafio01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;
import com.cielo.desafio01.enums.FeedbackStatus;
import com.cielo.desafio01.repository.FeedbackRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class QueuePollingService {
    private static final Logger logger = LoggerFactory.getLogger(QueuePollingService.class);

    private final SqsClient sqsClient;
    private final String suggestionQueueUrl;
    private final String complimentQueueUrl;
    private final String criticismQueueUrl;
    private final FeedbackRepository feedbackRepository;
    private final FeedbackService feedbackService;

    @Autowired
    public QueuePollingService(        
            SqsClient sqsClient,
            @Value("${sqs.suggestion.queue.url}") String suggestionQueueUrl,
            @Value("${sqs.compliment.queue.url}") String complimentQueueUrl,
            @Value("${sqs.criticism.queue.url}") String criticismQueueUrl,
            FeedbackRepository feedbackRepository,
            FeedbackService feedbackService) {
        this.sqsClient = sqsClient;
        this.suggestionQueueUrl = suggestionQueueUrl;
        this.complimentQueueUrl = complimentQueueUrl;
        this.criticismQueueUrl = criticismQueueUrl;
        this.feedbackRepository = feedbackRepository;
        this.feedbackService = feedbackService;

        logger.debug("Mensagem de Sugestão AINDA NÃO processada com sucesso: messageId={}", feedbackService);
    }

    @Scheduled(fixedRate = 10000)
    public void pollSuggestionQueue() {
        processQueue(suggestionQueueUrl, FeedbackStatus.FINALIZADO);
    }

    @Scheduled(fixedRate = 10000)
    public void pollComplimentQueue() {
        processQueue(complimentQueueUrl, FeedbackStatus.FINALIZADO);
    }

    @Scheduled(fixedRate = 10000)
    public void pollCriticismQueue() {
        processQueue(criticismQueueUrl, FeedbackStatus.FINALIZADO);
    }

    private void processQueue(String queueUrl, FeedbackStatus status) {
        ReceiveMessageResponse receiveMessageResponse = sqsClient.receiveMessage(
                ReceiveMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .maxNumberOfMessages(10)
                        .waitTimeSeconds(5)
                        .build());

        for (Message message : receiveMessageResponse.messages()) {
            System.out.println("Mensagem recebida: " + message.body());

            com.cielo.desafio01.model.Feedback feedback = new com.cielo.desafio01.model.Feedback();
            feedback.setMessage(message.body());
            feedback.setStatus(status);

            feedbackRepository.save(feedback);

            feedbackService.atualizarStatusFeedback(message.messageId(), status);

            sqsClient.deleteMessage(
                    DeleteMessageRequest.builder()
                            .queueUrl(queueUrl)
                            .receiptHandle(message.receiptHandle())
                            .build());
        }
    }
}