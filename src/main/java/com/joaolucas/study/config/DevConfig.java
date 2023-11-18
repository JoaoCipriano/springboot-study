package com.joaolucas.study.config;

import com.joaolucas.study.services.DBService;
import com.joaolucas.study.services.EmailService;
import com.joaolucas.study.services.SmtpEmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

import java.text.ParseException;

@Configuration
@Profile("dev")
public class DevConfig {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Bean
    public boolean instantiateDatabase(DBService dbService) throws ParseException {

        if (!"create".equalsIgnoreCase(strategy))
            return false;

        dbService.instantiateTestDatabase();
        return true;
    }

    @Bean
    public EmailService emailService(TemplateEngine templateEngine, JavaMailSender javaMailSender, MailSender mailSender) {
        return new SmtpEmailService(templateEngine, javaMailSender, mailSender);
    }
}
