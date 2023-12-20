package com.joaolucas.study.infrastructure.email.impl;

import com.joaolucas.study.infrastructure.email.AbstractEmailService;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

public class SmtpEmailService extends AbstractEmailService {

    private final MailSender mailSender;
    private final JavaMailSender javaMailSender;
    private final Logger logger;

    public SmtpEmailService(TemplateEngine templateEngine,
                            JavaMailSender javaMailSender,
                            MailSender mailSender,
                            Logger logger) {
        super(templateEngine, javaMailSender);
        this.mailSender = mailSender;
        this.javaMailSender = javaMailSender;
        this.logger = logger;
    }

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        logger.info("Enviando email...");
        mailSender.send(msg);
        logger.info("Email enviado");
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        logger.info("Enviando email...");
        javaMailSender.send(msg);
        logger.info("Email enviado");
    }

}
