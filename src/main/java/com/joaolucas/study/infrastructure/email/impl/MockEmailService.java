package com.joaolucas.study.infrastructure.email.impl;

import com.joaolucas.study.infrastructure.email.AbstractEmailService;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

public class MockEmailService extends AbstractEmailService {

    private final Logger logger;

    public MockEmailService(TemplateEngine templateEngine, JavaMailSender javaMailSender, Logger logger) {
        super(templateEngine, javaMailSender);
        this.logger = logger;
    }

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        var message = msg.toString();
        logger.info("Simulando envio de email...");
        logger.info(message);
        logger.info("Email enviado");
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        var message = msg.toString();
        logger.info("Simulando envio de email HTML...");
        logger.info(message);
        logger.info("Email enviado");
    }
}
