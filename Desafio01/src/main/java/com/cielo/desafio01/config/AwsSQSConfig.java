package com.cielo.desafio01.config;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;


@Configuration
public class AwsSQSConfig {

    @Value("${awsAccessKey}") 
    private String accessKey;

    @Value("${awsSecretKey}")
    private String secretKey;

    @Bean
    public AmazonSQS amazonSQS() {
        return AmazonSQSClient.builder()
                .withRegion("us-east-1")
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();
    }
}