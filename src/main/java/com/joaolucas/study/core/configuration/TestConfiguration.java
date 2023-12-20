package com.joaolucas.study.core.configuration;

import com.joaolucas.study.infrastructure.email.EmailService;
import com.joaolucas.study.infrastructure.email.impl.MockEmailService;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

@Configuration
@Profile("test")
public class TestConfiguration {

    @Bean
    public EmailService emailService(TemplateEngine templateEngine, JavaMailSender javaMailSender, Logger logger) {
        return new MockEmailService(templateEngine, javaMailSender, logger);
    }
}
