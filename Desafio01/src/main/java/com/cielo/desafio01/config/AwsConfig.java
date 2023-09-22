package com.cielo.desafio01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

@Configuration
public class AwsConfig {

    @Bean
    public AmazonSQS amazonSQS() {
        return AmazonSQSClientBuilder.defaultClient();
    }
}

