package com.xelari.presencebot.telegram.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@ConfigurationProperties(prefix = "bot")
@PropertySource("classpath:application.yml")
public class BotConfig {
    @Value("${name}")
    String botName;
    @Value("${token}")
    String token;
}
