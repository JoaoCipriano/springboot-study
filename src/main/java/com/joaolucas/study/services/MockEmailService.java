package com.joaolucas.study.services;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

@Slf4j
public class MockEmailService extends AbstractEmailService {

    public MockEmailService(TemplateEngine templateEngine, JavaMailSender javaMailSender) {
        super(templateEngine, javaMailSender);
    }

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        log.info("Simulando envio de email...");
        log.info(msg.toString());
        log.info("Email enviado");
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        log.info("Simulando envio de email HTML...");
        log.info(msg.toString());
        log.info("Email enviado");
    }
}
