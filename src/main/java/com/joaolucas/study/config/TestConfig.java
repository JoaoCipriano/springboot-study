package com.joaolucas.study.config;

import com.joaolucas.study.services.DBService;
import com.joaolucas.study.services.EmailService;
import com.joaolucas.study.services.MockEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

import java.text.ParseException;

@Configuration
@Profile("test")
public class TestConfig {

    @Bean
    public boolean instantiateDatabase(DBService dbService) throws ParseException {
        dbService.instantiateTestDatabase();
        return true;
    }

    @Bean
    public EmailService emailService(TemplateEngine templateEngine, JavaMailSender javaMailSender) {
        return new MockEmailService(templateEngine, javaMailSender);
    }
}
