package com.cielo.desafio01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.cielo.desafio01")
public class Desafio01Application {

    public static void main(String[] args) {
        SpringApplication.run(Desafio01Application.class, args);
    }
}