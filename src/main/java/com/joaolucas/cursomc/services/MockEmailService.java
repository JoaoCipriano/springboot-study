package com.joaolucas.cursomc.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

@Slf4j
public class MockEmailService extends AbstractEmailService {

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
