package com.cielo.desafio01.controller;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.amazonaws.services.sqs.model.GetQueueAttributesRequest;
import com.amazonaws.services.sqs.model.GetQueueAttributesResponse;
import com.amazonaws.services.sqs.model.QueueAttributeName;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResponse;
import com.amazonaws.services.sqs.model.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.cielo.desafio01.enums.FeedbackType;
import com.cielo.desafio01.model.CustomerFeedback;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    private final AmazonSQS amazonSQS;
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

    @PostMapping
    public ResponseEntity<String> enviarFeedback(@RequestBody CustomerFeedback feedback) {
        FeedbackType tipoFeedback = feedback.getType();
        String filaSQS = obterFilaSQSPorTipo(tipoFeedback);

        SendMessageRequest request = new SendMessageRequest()
                .withQueueUrl(filaSQS)
                .withMessageBody(feedback.getMessage());

        SendMessageResult result = amazonSQS.sendMessage(request);

        return ResponseEntity.status(HttpStatus.CREATED).body("Feedback enviado com sucesso.");
    }

    @GetMapping("/tamanho-fila/{tipo}")
    public ResponseEntity<Integer> obterTamanhoFilaPorTipo(@PathVariable FeedbackType tipo) {
        String filaSQS = obterFilaSQSPorTipo(tipo);
        int tamanhoFila = consultarTamanhoFilaSQS(filaSQS);

        return ResponseEntity.ok(tamanhoFila);
    }

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

        GetQueueAttributesResponse response = amazonSQS.getQueueAttributes(request);
        Map<String, String> attributes = response.attributes();

        String tamanhoAproximado = attributes.get(QueueAttributeName.ApproximateNumberOfMessages.toString());

        return Integer.parseInt(tamanhoAproximado);
    }

    private List<CustomerFeedback> consultarFeedbacksNoSQS(FeedbackType tipo) {
        String filaSQS = obterFilaSQSPorTipo(tipo);

        ReceiveMessageRequest request = new ReceiveMessageRequest()
                .withQueueUrl(filaSQS)
                .withMaxNumberOfMessages(10);

        ReceiveMessageResponse response = amazonSQS.receiveMessage(request);

        List<Message> messages = response.messages();

        List<CustomerFeedback> feedbacks = messages.stream()
                .map(this::converterMensagemParaFeedback)
                .collect(Collectors.toList());

        return feedbacks;
    }

    private CustomerFeedback converterMensagemParaFeedback(Message message) {
        CustomerFeedback feedback = new CustomerFeedback();
        feedback.setId(message.messageId());
        feedback.setType(FeedbackType.valueOf(message.messageAttributes().get("Type").stringValue()));
        feedback.setMessage(message.body());

        return feedback;
    }
}