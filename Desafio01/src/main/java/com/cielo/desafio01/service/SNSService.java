package com.cielo.desafio01.service;

import org.springframework.stereotype.Service;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;


@Service
public class SNSService {
    private final AmazonSNS snsClient;

    public SNSService() {
        this.snsClient = AmazonSNSClientBuilder.defaultClient();
    }

    public void enviarMensagemParaTopico(String topicoARN, String mensagem, String messageGroupId) {
        PublishRequest request = new PublishRequest()
                .withTopicArn(topicoARN)
                .withMessage(mensagem)
                .withMessageGroupId(messageGroupId)
                .withMessageDeduplicationId("mensagem-unica");
        try {
            snsClient.publish(request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao enviar a mensagem para o tópico SNS.");
        }
    }
}