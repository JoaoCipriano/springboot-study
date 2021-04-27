package com.joaolucas.cursomc.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

@Slf4j
public class SmtpEmailService extends AbstractEmailService {

    @Autowired
    private MailSender mailSender;

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        log.info("Enviando email...");
        mailSender.send(msg);
        log.info("Email enviado");
    }
}
