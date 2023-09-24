package com.cielo.desafio01.listener;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import org.springframework.beans.factory.annotation.Value;
import com.cielo.desafio01.enums.FeedbackStatus;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;
import com.cielo.desafio01.repository.FeedbackRepository;
import com.cielo.desafio01.service.FeedbackService;
import org.springframework.messaging.handler.annotation.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class FeedbackQueueListener {
    private static final Logger logger = LoggerFactory.getLogger(FeedbackQueueListener.class);
    @Value("https://sqs.us-east-1.amazonaws.com/315945136939/sqs-suggestion-queue.fifo")
    private String suggestionQueueUrl;
    @Value("${sqs.compliment.queue.url}")
    private String complimentQueueUrl;
    @Value("${sqs.criticism.queue.url}")
    private String criticismQueueUrl;

    private final FeedbackRepository feedbackRepository;
    private final SqsClient sqsClient;
    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackQueueListener(FeedbackRepository feedbackRepository, SqsClient sqsClient, FeedbackService feedbackService) {
        this.feedbackRepository = feedbackRepository;
        this.sqsClient = sqsClient;
        this.feedbackService = feedbackService;
    }

    @SqsListener("https://sqs.us-east-1.amazonaws.com/315945136939/sqs-suggestion-queue.fifo")
    public void processarMensagemSugestao(String mensagem, @Header("MessageId") String messageId, @Header("ReceiptHandle") String receiptHandle) {
        com.cielo.desafio01.model.Feedback feedback = new com.cielo.desafio01.model.Feedback();
        logger.debug("Mensagem de Sugestão AINDA NÃO processada com sucesso: messageId={}", messageId);
        feedback.setMessage(mensagem);
        feedback.setStatus(FeedbackStatus.FINALIZADO);

        feedbackRepository.save(feedback);

        System.out.println("Mensagem de Sugestão: " + mensagem);

        boolean sucesso = true;

        if (sucesso) {
            feedbackService.atualizarStatusFeedback(messageId, FeedbackStatus.FINALIZADO);
            sqsClient.deleteMessage(DeleteMessageRequest.builder()
                    .queueUrl(suggestionQueueUrl)
                    .receiptHandle(receiptHandle)
                    .build());
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
            sqsClient.deleteMessage(DeleteMessageRequest.builder()
                    .queueUrl(complimentQueueUrl)
                    .receiptHandle(receiptHandle)
                    .build());
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
            sqsClient.deleteMessage(DeleteMessageRequest.builder()
                    .queueUrl(criticismQueueUrl)
                    .receiptHandle(receiptHandle)
                    .build());
        }
    }
}
