package com.joaolucas.study.services;

import com.joaolucas.study.domain.Cliente;
import com.joaolucas.study.domain.Pedido;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(Pedido obj);

    void sendEmail(SimpleMailMessage msg);

    void sendOrderConfirmationHtmlEmail(Pedido obj);

    void sendHtmlEmail(MimeMessage msg);

    void sendNewPasswordEmail(Cliente cliente, String newPass);
}
