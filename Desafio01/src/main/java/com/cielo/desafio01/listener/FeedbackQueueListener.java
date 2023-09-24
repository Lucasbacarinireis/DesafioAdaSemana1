package com.cielo.desafio01.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.cielo.desafio01.enums.FeedbackStatus;
import com.cielo.desafio01.repository.FeedbackRepository;
import com.cielo.desafio01.service.FeedbackService;

import software.amazon.awssdk.services.guardduty.model.Feedback;
import org.springframework.messaging.handler.annotation.Header;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;

@Service
public class FeedbackQueueListener {
    private final FeedbackRepository feedbackRepository;
    private final AmazonSQS amazonSQS;
    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackQueueListener(FeedbackRepository feedbackRepository, AmazonSQS amazonSQS, FeedbackService feedbackService) {
        this.feedbackRepository = feedbackRepository;
        this.amazonSQS = amazonSQS;
        this.feedbackService = feedbackService;
    }

    @SqsListener("sqs-suggestion-queue.fifo")
    public void processarMensagemSugestao(String mensagem, @Header("MessageId") String messageId, @Header("ReceiptHandle") String receiptHandle) {
        com.cielo.desafio01.model.Feedback feedback = new com.cielo.desafio01.model.Feedback();
        feedback.setMessage(mensagem);
        feedback.setStatus("Finalizado");

        feedbackRepository.save(feedback);

        System.out.println("Mensagem de Sugestão: " + mensagem);

        boolean sucesso = true;

        if (sucesso) {
            feedbackService.atualizarStatusFeedback(messageId, FeedbackStatus.FINALIZADO);
            amazonSQS.deleteMessage(new DeleteMessageRequest().withQueueUrl("sqs-suggestion-queue.fifo").withReceiptHandle(receiptHandle));
        }
    }

    @SqsListener("sqs-compliment-queue.fifo")
    public void processarMensagemElogio(String mensagem, @Header("MessageId") String messageId, @Header("ReceiptHandle") String receiptHandle) {
        com.cielo.desafio01.model.Feedback feedback = new com.cielo.desafio01.model.Feedback();
        feedback.setMessage(mensagem);
        feedback.setStatus("Finalizado");

        feedbackRepository.save(feedback);

        System.out.println("Mensagem de Elogio: " + mensagem);

        boolean sucesso = true;

        if (sucesso) {
            feedbackService.atualizarStatusFeedback(messageId, FeedbackStatus.FINALIZADO);
            amazonSQS.deleteMessage(new DeleteMessageRequest().withQueueUrl("sqs-compliment-queue.fifo").withReceiptHandle(receiptHandle));
        }
    }

    @SqsListener("sqs-criticism-queue.fifo")
    public void processarMensagemCritica(String mensagem, @Header("MessageId") String messageId, @Header("ReceiptHandle") String receiptHandle) {
        com.cielo.desafio01.model.Feedback feedback = new com.cielo.desafio01.model.Feedback();
        feedback.setMessage(mensagem);
        feedback.setStatus("Finalizado");

        feedbackRepository.save(feedback);

        System.out.println("Mensagem de Crítica: " + mensagem);

        boolean sucesso = true;

        if (sucesso) {
            feedbackService.atualizarStatusFeedback(messageId, FeedbackStatus.FINALIZADO);
            amazonSQS.deleteMessage(new DeleteMessageRequest().withQueueUrl("sqs-criticism-queue.fifo").withReceiptHandle(receiptHandle));
        }
    }
}