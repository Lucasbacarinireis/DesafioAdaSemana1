package com.cielo.desafio01.listener;

import com.amazonaws.services.sqs.AmazonSQS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.cielo.desafio01.enums.FeedbackStatus;

import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

import com.cielo.desafio01.repository.FeedbackRepository;
import com.cielo.desafio01.service.FeedbackService;

import org.springframework.messaging.handler.annotation.Header;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FeedbackQueueListener {
    private static final Logger logger = LoggerFactory.getLogger(FeedbackQueueListener.class);
    @Value("${sqs.suggestion.queue.url}")
    private String suggestionQueueUrl;
    @Value("${sqs.compliment.queue.url}")
    private String complimentQueueUrl;
    @Value("${sqs.criticism.queue.url}")
    private String criticismQueueUrl;

    private final FeedbackRepository feedbackRepository;
    private final AmazonSQS amazonSQS;
    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackQueueListener(FeedbackRepository feedbackRepository, AmazonSQS amazonSQS, FeedbackService feedbackService) {
        this.feedbackRepository = feedbackRepository;
        this.amazonSQS = amazonSQS;
        this.feedbackService = feedbackService;
    }

    @SqsListener("${sqs.suggestion.queue.url}")
    public void processarMensagemSugestao(String mensagem, @Header("MessageId") String messageId, @Header("ReceiptHandle") String receiptHandle) {
        logger.debug("Recebida mensagem de Sugestão: {}", mensagem);
        com.cielo.desafio01.model.Feedback feedback = new com.cielo.desafio01.model.Feedback();
        feedback.setMessage(mensagem);
        feedback.setStatus(FeedbackStatus.FINALIZADO);

        feedbackRepository.save(feedback);

        System.out.println("Mensagem de Sugestão: " + mensagem);

        boolean sucesso = true;

        if (sucesso) {
            feedbackService.atualizarStatusFeedback(messageId, FeedbackStatus.FINALIZADO);
            amazonSQS.deleteMessage(new DeleteMessageRequest().withQueueUrl(suggestionQueueUrl).withReceiptHandle(receiptHandle));
        }
        logger.debug("Mensagem de Sugestão processada com sucesso: messageId={}", messageId);
    }

    @SqsListener("${sqs.compliment.queue.url}")
    public void processarMensagemElogio(String mensagem, @Header("MessageId") String messageId, @Header("ReceiptHandle") String receiptHandle) {
        com.cielo.desafio01.model.Feedback feedback = new com.cielo.desafio01.model.Feedback();
        feedback.setMessage(mensagem);
        feedback.setStatus(FeedbackStatus.FINALIZADO);

        feedbackRepository.save(feedback);

        System.out.println("Mensagem de Elogio: " + mensagem);

        boolean sucesso = true;

        if (sucesso) {
            feedbackService.atualizarStatusFeedback(messageId, FeedbackStatus.FINALIZADO);
            amazonSQS.deleteMessage(new DeleteMessageRequest().withQueueUrl(complimentQueueUrl).withReceiptHandle(receiptHandle));
        }
    }

    @SqsListener("${sqs.criticism.queue.url}")
    public void processarMensagemCritica(String mensagem, @Header("MessageId") String messageId, @Header("ReceiptHandle") String receiptHandle) {
        com.cielo.desafio01.model.Feedback feedback = new com.cielo.desafio01.model.Feedback();
        feedback.setMessage(mensagem);
        feedback.setStatus(FeedbackStatus.FINALIZADO);

        feedbackRepository.save(feedback);

        System.out.println("Mensagem de Crítica: " + mensagem);

        boolean sucesso = true;

        if (sucesso) {
            feedbackService.atualizarStatusFeedback(messageId, FeedbackStatus.FINALIZADO);
            amazonSQS.deleteMessage(new DeleteMessageRequest().withQueueUrl(criticismQueueUrl).withReceiptHandle(receiptHandle));
        }
    }
}