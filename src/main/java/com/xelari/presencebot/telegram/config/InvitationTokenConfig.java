package com.xelari.presencebot.telegram.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Component
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "invitation")
public class InvitationTokenConfig {

    @Value("${token-expiration-days}")
    private int expirationDays;

}

