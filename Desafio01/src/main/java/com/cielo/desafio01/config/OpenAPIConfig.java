package com.cielo.desafio01.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${desafio-ada.openapi.dev-url}")
    private String devUrl;

    @Value("${desafio-ada.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL em Desenvolvimento");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL em Produção");

        Contact contact = new Contact();
        contact.setEmail("lucasbacarinireis1@gmail.com");
        contact.setName("Lucas " +
                "Anderson " +
                "Jardielma ");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Desafio Ada-Cielo")
                .version("1.0")
                .contact(contact)
                .description("Esta API expõe endpoints para trabalhar com feedbacks").termsOfService("https://www.google.com.br/")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}
