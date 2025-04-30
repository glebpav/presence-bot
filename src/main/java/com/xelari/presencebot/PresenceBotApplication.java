package com.xelari.presencebot;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PresenceBotApplication {
    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.configure().directory("env/.env").load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        SpringApplication.run(PresenceBotApplication.class, args);

    }
}
