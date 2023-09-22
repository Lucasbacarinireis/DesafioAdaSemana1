package com.cielo.desafio01.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.cielo.desafio01.enums.FeedbackStatus;
import com.cielo.desafio01.model.CustomerFeedback;
import com.cielo.desafio01.service.SNSService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.cielo.desafio01.enums.FeedbackType;
import com.cielo.desafio01.enums.FeedbackStatus;
import com.cielo.desafio01.model.CustomerFeedback;

@RestController
public class FeedbackControllerPost {

    @Autowired
    private SNSService snsService;

    @Value("${sns.suggestion.topic.arn}")
    private String suggestionTopicArn;

    @Value("${sns.compliment.topic.arn}")
    private String complimentTopicArn;

    @Value("${sns.criticism.topic.arn}")
    private String criticismTopicArn;

    @Operation(
            summary = "Envia um feedback para a fila"
//            description = "Envia um feedback para a fila.",
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = CustomerFeedback.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/enviar-feedback")
    public ResponseEntity<String> enviarFeedback(@RequestBody CustomerFeedback feedback) {
        FeedbackType tipoFeedback = feedback.getType();
        String topicoSNS = obterTopicoSNSPorTipo(tipoFeedback);

        feedback.setStatus(FeedbackStatus.RECEBIDO);

        String messageGroupId = obterMessageGroupId(feedback);

        String mensagemJson = criarMensagemJson(feedback);

        snsService.enviarMensagemParaTopico(topicoSNS, mensagemJson, messageGroupId);

        return ResponseEntity.status(HttpStatus.CREATED).body("Feedback enviado com sucesso.");
    }

    private String obterMessageGroupId(CustomerFeedback feedback) {
        return feedback.getType().toString();
    }

    private String obterTopicoSNSPorTipo(FeedbackType tipoFeedback) {
        switch (tipoFeedback) {
            case SUGESTAO:
                return suggestionTopicArn;
            case ELOGIO:
                return complimentTopicArn;
            case CRITICA:
                return criticismTopicArn;
            default:
                throw new IllegalArgumentException("Tipo de feedback desconhecido: " + tipoFeedback);
        }
    }

    private String criarMensagemJson(CustomerFeedback feedback) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String mensagemJson = objectMapper.writeValueAsString(feedback);
            return mensagemJson;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao converter o objeto CustomerFeedback em JSON.");
        }
}
}