package com.cielo.desafio01.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.cielo.desafio01.enums.FeedbackStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Service
public class FeedbackQueueConsumer {

    private final AmazonSQS amazonSQS;
    private final FeedbackService feedbackService;
    private final List<String> filaSQSUrls;

    @Autowired
    public FeedbackQueueConsumer(
            AmazonSQS amazonSQS,
            FeedbackService feedbackService,
            @Value("${sqs.suggestion.queue.url}") String sqsSuggestionQueueUrl,
            @Value("${sqs.compliment.queue.url}") String sqsComplimentQueueUrl,
            @Value("${sqs.criticism.queue.url}") String sqsCriticismQueueUrl
    ) {
        this.amazonSQS = amazonSQS;
        this.feedbackService = feedbackService;
        this.filaSQSUrls = List.of(sqsSuggestionQueueUrl, sqsComplimentQueueUrl, sqsCriticismQueueUrl);
    }

    @Scheduled(fixedDelay = 10000)
    public void processarMensagensDasFilas() {
        for (String filaSQSUrl : filaSQSUrls) {
            ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest()
                    .withQueueUrl(filaSQSUrl)
                    .withMaxNumberOfMessages(10);

            List<Message> messages = amazonSQS.receiveMessage(receiveMessageRequest).getMessages();

            for (Message message : messages) {
                try {
                    String mensagem = message.getBody();
                    String feedbackId = message.getMessageAttributes().get("FeedbackId").getStringValue();

                    feedbackService.atualizarStatusFeedback(feedbackId, FeedbackStatus.EM_PROCESSAMENTO);

                    boolean sucesso = processarMensagemComSucesso(mensagem);

                    if (sucesso) {
                        feedbackService.atualizarStatusFeedback(feedbackId, FeedbackStatus.FINALIZADO);

                        String receiptHandle = message.getReceiptHandle();
                        amazonSQS.deleteMessage(new DeleteMessageRequest().withQueueUrl(filaSQSUrl).withReceiptHandle(receiptHandle));
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    private boolean processarMensagemComSucesso(String mensagem) {
        return true;
    }
}
