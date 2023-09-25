package com.cielo.desafio01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.cielo.desafio01.config.AwsSQSConfig;

@SpringBootApplication
@Import(AwsSQSConfig.class)
public class Desafio01Application {

    public static void main(String[] args) {
        SpringApplication.run(Desafio01Application.class, args);
    }
}