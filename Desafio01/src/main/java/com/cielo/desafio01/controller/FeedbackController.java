package com.cielo.desafio01.controller;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cielo.desafio01.enums.FeedbackType;
import com.cielo.desafio01.model.CustomerFeedback;

@Tag(name = "Feedback", description = "API de Gerenciamento de feedbacks")
@RestController
@RequestMapping( value = "/feedbacks")
//@Api(value= "API REST Feedbacks")
//@CrossOrigin(origins = "*")
public class FeedbackController {

    private AmazonSQS amazonSQS;
    private final String sqsSuggestionQueueUrl;
    private final String sqsComplimentQueueUrl;
    private final String sqsCriticismQueueUrl;


    @Autowired
    public FeedbackController(
            AmazonSQS amazonSQS,
            @Value("${sqs.suggestion.queue.url}") String sqsSuggestionQueueUrl,
            @Value("${sqs.compliment.queue.url}") String sqsComplimentQueueUrl,
            @Value("${sqs.criticism.queue.url}") String sqsCriticismQueueUrl
    ) {
        this.amazonSQS = amazonSQS;
        this.sqsSuggestionQueueUrl = sqsSuggestionQueueUrl;
        this.sqsComplimentQueueUrl = sqsComplimentQueueUrl;
        this.sqsCriticismQueueUrl = sqsCriticismQueueUrl;
    }


    @Operation(
            summary = "Envia um feedback para a fila"
//            description = "Envia um feedback para a fila.",
            )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = CustomerFeedback.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping
    public ResponseEntity<String> enviarFeedback(@RequestBody CustomerFeedback feedback) {
        FeedbackType tipoFeedback = feedback.getType();
        String filaSQS = obterFilaSQSPorTipo(tipoFeedback);

        String messageGroupId = determinarMessageGroupId(feedback);

        SendMessageRequest request = new SendMessageRequest()
                .withQueueUrl(filaSQS)
                .withMessageBody(feedback.getMessage())
                .withMessageGroupId(messageGroupId)
                .withMessageDeduplicationId("mensagem-unica");

        SendMessageResult result = amazonSQS.sendMessage(request);

        return ResponseEntity.status(HttpStatus.CREATED).body("Feedback enviado com sucesso.");
    }

    private String determinarMessageGroupId(CustomerFeedback feedback) {
        return feedback.getType().toString();
    }

    @Operation(
            summary = "Busca o tamanho da fila pelo tipo"
//            description = "Envia um feedback para a fila.",
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = CustomerFeedback.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/tamanho-fila/{tipo}")
    public ResponseEntity<Integer> obterTamanhoFilaPorTipo(@PathVariable FeedbackType tipo) {
        String filaSQS = obterFilaSQSPorTipo(tipo);
        int tamanhoFila = consultarTamanhoFilaSQS(filaSQS);

        return ResponseEntity.ok(tamanhoFila);
    }

    @Operation(
            summary = "Busca todos os feedbacks"
//            description = "Envia um feedback para a fila.",
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = CustomerFeedback.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/todos/{tipo}")
    public ResponseEntity<List<CustomerFeedback>> obterTodosFeedbacksPorTipo(@PathVariable FeedbackType tipo) {
        List<CustomerFeedback> feedbacks = consultarFeedbacksNoSQS(tipo);

        return ResponseEntity.ok(feedbacks);
    }

    private String obterFilaSQSPorTipo(FeedbackType tipo) {
        switch (tipo) {
            case SUGESTAO:
                return sqsSuggestionQueueUrl;
            case ELOGIO:
                return sqsComplimentQueueUrl;
            case CRITICA:
                return sqsCriticismQueueUrl;
            default:
                throw new IllegalArgumentException("Tipo de feedback inválido: " + tipo);
        }
    }

    private int consultarTamanhoFilaSQS(String filaSQS) {
        GetQueueAttributesRequest request = new GetQueueAttributesRequest()
                .withQueueUrl(filaSQS)
                .withAttributeNames(QueueAttributeName.ApproximateNumberOfMessages);

        GetQueueAttributesResult response = amazonSQS.getQueueAttributes(request);
        Map<String, String> attributes = response.getAttributes();

        String tamanhoAproximado = attributes.get(QueueAttributeName.ApproximateNumberOfMessages.toString());

        return Integer.parseInt(tamanhoAproximado);
    }

    private List<CustomerFeedback> consultarFeedbacksNoSQS(FeedbackType tipo) {
        String filaSQS = obterFilaSQSPorTipo(tipo);

        ReceiveMessageRequest request = new ReceiveMessageRequest()
                .withQueueUrl(filaSQS)
                .withMaxNumberOfMessages(10);

        ReceiveMessageResult response = amazonSQS.receiveMessage(request);

        List<Message> messages = response.getMessages();

        List<CustomerFeedback> feedbacks = messages.stream()
                .map(this::converterMensagemParaFeedback)
                .collect(Collectors.toList());

        return feedbacks;
    }

    private CustomerFeedback converterMensagemParaFeedback(Message message) {
        CustomerFeedback feedback = new CustomerFeedback();
        feedback.setId(message.getMessageId());
        feedback.setType(FeedbackType.valueOf(message.getMessageAttributes().get("Type").getStringValue()));
        feedback.setMessage(message.getBody());

        return feedback;
    }
}