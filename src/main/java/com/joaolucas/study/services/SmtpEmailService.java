package com.joaolucas.study.services;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

@Slf4j
public class SmtpEmailService extends AbstractEmailService {

    private final MailSender mailSender;

    private final JavaMailSender javaMailSender;

    public SmtpEmailService(TemplateEngine templateEngine, JavaMailSender javaMailSender, MailSender mailSender) {
        super(templateEngine, javaMailSender);
        this.mailSender = mailSender;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        log.info("Enviando email...");
        mailSender.send(msg);
        log.info("Email enviado");
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        log.info("Enviando email...");
        javaMailSender.send(msg);
        log.info("Email enviado");
    }

}
