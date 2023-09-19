package com.cielo.desafio01.util;

public class EnvironmentVariable {

    public static void main(String[] args) {
        String awsAccessKey = System.getenv("AWS_ACCESS_KEY_ID");
        String awsSecretKey = System.getenv("AWS_SECRET_ACCESS_KEY");

    }
}